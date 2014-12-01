package hw.lab3.s1011542.reversi;

import hw.lab3.s1011542.reversi.PiecesHistory.State;
import hw.lab3.s1011542.reversi.PiecesHistory.State.Step;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class Reversi {
	
	private final String LOG_TAG = "Reversi";
	
	private final int dim = 8; 
	
	/* callback for GameState */
	private ReversiGameState delegate = null;
	
	
	private boolean gameOver = false;
	
	/* the board */
	private PiecesType board[][] = null;
	
	private List<PiecesPos> posiible = null;
	
	/* the current player */
	private PiecesType currentPlayer;
	
	
	private int level;
	
	private PiecesHistory history = null;


	/* Constructor for initial game */
	public Reversi(int level) {

		this.level = level;
		this.history = new PiecesHistory();
		this.posiible = new ArrayList<PiecesPos>();		
		/* clean possible list */
		this.posiible.clear();
		
		/* allocate board */
		this.board = null;
		this.board = new PiecesType[dim][dim];
		/* set first player */
		this.currentPlayer = PiecesType.BLACK;
		this.history.next(this.currentPlayer);

		this.gameOver = false;
		
		/* init the board */
		
		for(int i = 0 ; i < dim ; i++)
			for(int j = 0 ; j < dim ; j++)
				this.board[i][j] = PiecesType.EMPTY;
		
		/* set center four pieces */
		int center = (dim-1) /2 ;
		
		this.board[center][center] 
				= this.board[center+1][center+1] 
				= PiecesType.BLACK;
		this.board[center+1][center]
				= this.board[center][center+1]
				= PiecesType.WHITE;
		
	
		this.calulatePossible();
		
		/* notify game state */
		if ( this.delegate != null) {
			this.delegate.GameStart();
			this.delegate.RefreshGame();
		}
		
	}
	
	public PiecesType [][] getBoard() {
		return this.board;
	}
	
	public PiecesType getCurenntPlayer() {
		return this.currentPlayer;
	}
	
	public List<PiecesPos> getPossiblePos() {
		return this.posiible;
	}
	
	public boolean isGameOver() {
		return this.gameOver;
	}
	
	public int getPiecesCount(PiecesType type) {
		int count = 0;
		for( int i = 0; i < this.dim; i++) {
			for( int j = 0; j < this.dim; j++) {
				if( this.board[i][j] == type)
					count ++;
			}
		}
		return count;
	}
	
	public PiecesHistory getHistory() {
		return this.history;
	}
	
	public void setHistory(PiecesHistory history) {
		this.history = history;
	}
	
	public void setBoard(PiecesType board[][]) {
		this.board = board;
	}
	
	public PiecesType getWinner() {
		if ( !this.isGameOver() )
			return null;
		return this.getPiecesCount(PiecesType.BLACK) > this.getPiecesCount(PiecesType.WHITE) ? PiecesType.BLACK : PiecesType.WHITE;
	}

	public void back() {
		State state = this.history.back();
		if( state != null) {
			this.currentPlayer = state.player;
			for(int i = 0 ; i < state.steps.size(); i++) {
				this.board[state.steps.get(i).pos.i][state.steps.get(i).pos.j] = state.steps.get(i).type;
			}
		}
		this.calulatePossible();
	}
	
	public void move(PiecesPos pos) {
		
		if(this.gameOver)
			return;
		if( !isMoveValid(pos)) 
			return;	
		

		this.clearPossibleMove();
		this.movePeices(pos, true);
		
		this.currentPlayer = this.currentPlayer == PiecesType.BLACK ? PiecesType.WHITE : PiecesType.BLACK;
		this.clearPossibleMove();
		for(int i = 0 ; i < this.level ; i++){
			this.addForbidden();
		}
		this.calulatePossible();
		
		if( this.posiible.size() != 0) {
			if(this.delegate != null)
				this.delegate.ChangePlayer();
		} else {

			this.currentPlayer = this.currentPlayer == PiecesType.BLACK ? PiecesType.WHITE : PiecesType.BLACK;
			this.calulatePossible();	
			
			if(this.posiible.size() == 0) {
				this.gameOver = true;
				if(this.delegate != null)
					this.delegate.GameOver();
			}
			
		}

		this.history.next(this.currentPlayer);
		
		if(this.delegate != null) {
			this.delegate.RefreshGame();
		}
	}
	
	public void setDelegate(ReversiGameState delegate) {
		this.delegate = delegate;
	}
	
	private void calulatePossible() {
		this.clearPossibleMove();
		for( int i = 0 ; i < dim ; i++) {
			for( int j = 0 ; j < dim ; j++) {
				PiecesPos p = new PiecesPos(i,j);
				int total = this.movePeices(p, false);
				if ( total > 0 && this.board[i][j] == PiecesType.EMPTY) {
					this.board[p.i][p.j] = PiecesType.POSSIBLE;
					this.posiible.add(p);
				}
					
			}
		}
	}
	
	private int movePeices(PiecesPos pos , boolean doMove) {
		
		int total = 0;
		
		for ( int oi = -1 ; oi <= 1 ; oi ++) {
			for ( int oj = -1 ; oj <= 1 ; oj++) {
				
				if( oi == 0 && oj == 0)
					continue;
				
				for( int step = 1 ; step < dim ; step++ ) {
					
					int step_i = pos.i + oi* step;
					int step_j  = pos.j + oj* step;
					
					/* out of bounds */
					if( step_i < 0 || step_j < 0 || step_i >= dim || step_j >= dim)
						break;
					
					PiecesType stepType = this.board[step_i][step_j];
					
					if( stepType == PiecesType.EMPTY || stepType == PiecesType.FORBIDDEN || stepType == PiecesType.POSSIBLE)
						break;
					
					if( stepType == this.currentPlayer) {
						if( step > 1) {
							total += step - 1;
							if( doMove) {
								while(step-- > 0) {
									PiecesPos hpos = new PiecesPos(pos.i + oi* step,pos.j + oj* step);
									Step hstep = new Step(hpos, board[ pos.i + oi* step ][ pos.j + oj* step]);
									this.history.add(hstep);
									board[ pos.i + oi* step ][ pos.j + oj* step] = this.currentPlayer;
								}				
							}
						}
						break;
					}
				}
			}
		}
		return total;
	}
	
	
	private boolean isMoveValid(PiecesPos pos) {
		for( PiecesPos p : this.posiible) {
			if(p.equals(pos)) {
				return true;
			}
		}
		return false;
	}
	
	private List<PiecesPos> getPossibleMove() {
		return this.posiible;
	}
	
	private void clearPossibleMove() {
		this.posiible.clear();
		
		for( int i = 0 ; i < dim ; i++)
			for( int j = 0 ; j < dim ; j++)
				if(this.board[i][j] == PiecesType.POSSIBLE)
					this.board[i][j] = PiecesType.EMPTY;
	}
	
	private void addForbidden() {
		
		int type = ((Double)(Math.random() * 4)).intValue();
		
		PiecesPos start = null;
		
		switch(type) {
			case 0:
				start = new PiecesPos(0,0);
				break;
			case 1:
				start = new PiecesPos(0,this.dim-1);
				break;
			case 2:
				start = new PiecesPos(this.dim-1,0);
				break;
			case 3:
				start = new PiecesPos(this.dim-1,this.dim-1);
				break;
			default:
				start = new PiecesPos(0,0);
				break;
		}
		
		if( this.board[0][0] == PiecesType.EMPTY || this.board[0][0] == PiecesType.POSSIBLE) {
			Step hstep = new Step(start, board[0][0]);
			this.history.add(hstep);
			this.board[0][0] = PiecesType.FORBIDDEN;
			return;
		}
		if( this.board[this.dim-1][0] == PiecesType.EMPTY || this.board[this.dim-1][0] == PiecesType.POSSIBLE) {
			Step hstep = new Step(start, board[this.dim-1][0]);
			this.history.add(hstep);
			this.board[this.dim-1][0] = PiecesType.FORBIDDEN;
			return;
		}
		if( this.board[0][this.dim-1] == PiecesType.EMPTY || this.board[0][this.dim-1] == PiecesType.POSSIBLE) {
			Step hstep = new Step(start, board[0][this.dim-1]);
			this.history.add(hstep);
			this.board[0][this.dim-1] = PiecesType.FORBIDDEN;
			return;
		}
		if( this.board[this.dim-1][this.dim-1] == PiecesType.EMPTY || this.board[this.dim-1][this.dim-1] == PiecesType.POSSIBLE) {
			Step hstep = new Step(start, board[this.dim-1][this.dim-1]);
			this.history.add(hstep);
			this.board[this.dim-1][this.dim-1] = PiecesType.FORBIDDEN;
			return;
		}
		
		int i = 0 ; 
		int j = 0;
		
		List<PiecesPos> pos = new ArrayList<PiecesPos>();
		List<PiecesPos> traveled = new ArrayList<PiecesPos>();
		pos.add(start);
		
		while(!pos.isEmpty()) {
			
			PiecesPos p = pos.get(0);
			
			i = p.i;
			j = p.j;
			
			if( i < 0 || j < 0 || i >= this.dim || j >= this.dim) {
				pos.remove(0);
				continue;
			}
			
			if( this.board[i][j] == PiecesType.EMPTY) {
				Step hstep = new Step(start, board[i][j]);
				this.history.add(hstep);
				this.board[i][j] = PiecesType.FORBIDDEN;
				pos.clear();
				continue;
			}
			
			traveled.add(p);
			
			PiecesPos up = new PiecesPos(i-1,j);
			PiecesPos down = new PiecesPos(i+1,j);
			PiecesPos left = new PiecesPos(i,j-1);
			PiecesPos right = new PiecesPos(i,j+1);
			
			for( int k = 0 ; k < traveled.size(); k++) {
				if(traveled.get(k).i == up.i && traveled.get(k).j == up.j)
					break;
				if( k == traveled.size()-1)
					pos.add(up);
			}
			for( int k = 0 ; k < traveled.size(); k++) {
				if(traveled.get(k).i == down.i && traveled.get(k).j == down.j)
					break;
				if( k == traveled.size()-1)
					pos.add(down);
			}
			for( int k = 0 ; k < traveled.size(); k++) {
				if(traveled.get(k).i == left.i && traveled.get(k).j == left.j)
					break;
				if( k == traveled.size()-1)
					pos.add(left);
			}
			for( int k = 0 ; k < traveled.size(); k++) {
				if(traveled.get(k).i == right.i && traveled.get(k).j == right.j)
					break;
				if( k == traveled.size()-1)
					pos.add(right);
			}
			pos.remove(0);
		}
		
	}
}
