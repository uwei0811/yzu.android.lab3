package hw.lab3.s1011542;

import hw.lab3.s1011542.reversi.PiecesHistory;
import hw.lab3.s1011542.reversi.PiecesPos;
import hw.lab3.s1011542.reversi.PiecesType;
import hw.lab3.s1011542.reversi.Reversi;
import hw.lab3.s1011542.reversi.ReversiGameState;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class BoadView extends View implements ReversiGameState,OnTouchListener{
	
	private Reversi game = null;
	private Music gray_music;
	private Handler mHandler;
	private Thread mThread;
	private int Clock;
	private final int Defaulf_Clock_time = 15;
	private boolean Thread_lock;
	
	public BoadView(Activity context) {
		super(context);
		this.game = new Reversi(0);
		this.game.setDelegate(this);
		this.gray_music = new Music(context);
		this.setOnTouchListener(this);
		this.Thread_lock = true;
		this.Clock = Defaulf_Clock_time;
		this.setclk();
		this.mThread.start();
	}
	
	public BoadView(Activity context, Reversi game, int time) {
		super(context);
		this.game = game;
		this.game.setDelegate(this);
		this.gray_music = new Music(context);
		this.setOnTouchListener(this);
		this.Thread_lock = true;
		this.setclk();
		if(time > 0)
			this.Clock = time;
		
		this.mThread.start();
	}
	
	private void setclk()
	{
		this.Clock = this.Defaulf_Clock_time;
		this.mThread = new Thread(){
		    @Override
		    public void run() {
		    	
		        while(Thread_lock){
		            try{
		                Message msg = new Message();
		                msg.what = 1;
		                mHandler.sendMessage(msg);
		                Thread.sleep(1000);
		            }
		            catch(Exception e){
		                e.printStackTrace();
		            }
		        }
		    }            
		};
		
		mHandler = new Handler(){
		    @Override
		    public void handleMessage(Message msg) {
		        Clock = Clock - 1;
		        RefreshClk();
		        if(Clock == 0)
		        	Time_out();
		        super.handleMessage(msg);
		    }  
		};
	}
	
	private void Time_out()
	{
		rest_clk();
		if(game != null)
			game.foceChangePlayer();
	}
	
	private void rest_clk()
	{
		Clock = Defaulf_Clock_time;
	}
	
	public void reset()
	{
		rest_clk();
		game = null;
		game = new Reversi(0);
		game.setDelegate(this);
		RefreshGame();
	}
	
	public int get_clk()
	{
		return Clock;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		PiecesType board[][] = game.getBoard();
		Paint p = new Paint();  
		
		//paint to black�色
		p.setColor(Color.BLACK);
		//paint to stroke
		p.setStyle(Paint.Style.STROKE);
		//paint stroke width to 5
		p.setStrokeWidth(5);
		
		//draw board
		canvas.drawRect(0, 0, 480, 480, p);
		for(int i = 0 ; i < 8;i++)
			canvas.drawLine(i*60, 0, i*60, 480, p);
		for(int i = 0 ; i < 8;i++)
			canvas.drawLine(0, i*60, 480, i*60, p);
		p.setStyle(Paint.Style.FILL);
		
		//draw all node
		for(int col = 0 ; col < 8 ; col++) {
			for(int row = 0 ; row < 8 ; row++) {
				if(board[row][col] == PiecesType.EMPTY)
				{//empty
					p.setColor(Color.BLACK);
					canvas.drawLine(row*60, col*60, (row+1)*60, (col+1)*60, p);
					canvas.drawLine((row+1)*60, col*60, row*60, (col+1)*60, p);
				}else if(board[row][col] == PiecesType.BLACK)
				{//BLACK
					p.setColor(Color.BLACK);
					canvas.drawCircle(row*60+30, col*60+30, 25, p);
				}else if(board[row][col] == PiecesType.WHITE)
				{//WHITE
					p.setColor(Color.WHITE);
					canvas.drawCircle(row*60+30, col*60+30, 25, p);
				}else if(board[row][col] == PiecesType.FORBIDDEN)
				{//FORBIDDEN
					p.setColor(Color.GRAY);
					canvas.drawRect(row*60+2,col*60+2,(row+1)*60-2,(col+1)*60-2,p);
				}
			}
		}
		
		if(game.getCurenntPlayer() == PiecesType.BLACK)
			p.setColor(Color.BLACK);
		else
			p.setColor(Color.WHITE);
		
		if(!game.isGameOver())
			canvas.drawCircle(235, 510, 20, p);
		
		p.setTextSize(30);
		p.setColor(Color.BLACK);
		if(!game.isGameOver())
			canvas.drawText("NOW PLAYER :        倒數時間" + Clock+ "s", 10, 520, p);
		else
			canvas.drawText("GAME OVER", 10, 520, p);
		
		if(game.isGameOver())
		{
			p.setColor(Color.BLACK);
			p.setAlpha(180);
			canvas.drawRect(0, 0, 480, 480, p);
			p.setStyle(Paint.Style.FILL);
			
			p.setTextSize(50);
			p.setColor(Color.WHITE);
			if(game.getWinner() == PiecesType.BLACK)
				canvas.drawText("WINNER is BLACK" , 90, 120, p);
			else
				canvas.drawText("WINNER is WHITE" , 90, 120, p);
			canvas.drawText("BLACK:" + game.getPiecesCount(PiecesType.BLACK), 90, 200, p);
			canvas.drawText("WHITE:" + game.getPiecesCount(PiecesType.WHITE), 90, 280, p);
		}
	}
	
	private void RefreshClk() {
		this.invalidate();
	}
	
	@Override
	public void GameStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RefreshGame() {
		rest_clk();
		this.invalidate();
		Log.d("REfa" , "-");
	}

	@Override
	public void ChangePlayer() {
		gray_music.start();
	}

	@Override
	public void GameOver() {
		// TODO Auto-generated method stub
		this.invalidate();
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
       	int iEventType = event.getAction();
       	PiecesType board[][] = game.getBoard();
       	
       	if(iEventType == MotionEvent.ACTION_DOWN){

           	float x = event.getX();
           	float y = event.getY();

       		int row = (int) (x / 60);
       		int col = (int) (y / 60);
       		PiecesPos pos = new PiecesPos(row , col);

       		if(row < 8 && col < 8)
       			game.move(pos);
       		Log.d("mouse touch","["+col+"]"+"["+row+"]") ;
       		
       	}
       	
       	return true;
	}
	
	public PiecesType[][] get_Board()
	{
		return game.getBoard();
	}
	
	public void back()
	{
		game.back();
		this.invalidate();
	}
	
	public void setPause() {
		this.Thread_lock = false;

	}
	
	public void setResume() {
		this.Thread_lock = true;
	}

	public PiecesType getCurenntPlayer() {
		return this.game.getCurenntPlayer();
	}
	
	public PiecesHistory getHistory() {
		return this.game.getHistory();
	}
	
	public void setHistory(PiecesHistory history) {
		this.game.setHistory(history);
	}
	
	public void setClock(int Clock) {
		this.Clock = Clock;
	}
	
	public int getClock() {
		return this.Clock;
	}
	
	public void setGame(Reversi game) {
		this.game = game;
	}
	
	public Reversi getGame() {
		return this.game;
	}
	

	
}
