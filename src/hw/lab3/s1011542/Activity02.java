package hw.lab3.s1011542;

import hw.lab3.s1011542.R;
import hw.lab3.s1011542.reversi.PiecesHistory;
import hw.lab3.s1011542.reversi.PiecesHistory.State.Step;
import hw.lab3.s1011542.reversi.PiecesPos;
import hw.lab3.s1011542.reversi.Reversi;
import hw.lab3.s1011542.reversi.PiecesHistory.State;
import hw.lab3.s1011542.reversi.PiecesType;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Activity02 extends Activity {
	private Intent intent;
	private RelativeLayout ly02;
	private ImageButton exit;
	private ImageButton reset;
	private ImageButton back;
	private ImageButton st01;
	private ImageButton st02;
	private ImageButton st03;
	private ImageButton st04;
	private ImageButton st05;
	private BoadView view;
	private SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity02);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		int level = bundle.getInt("level");	
		init();
		setlinster();
		Log.d("level",""+level);
		view = new BoadView(this,level);
		
		ly02.addView(view, 480, 530);
	}

	@Override
	protected void onPause() {
		saveData();
		super.onPause();
	}
	
	@Override
	protected void onRestart() {
		setContentView(R.layout.activity02);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		int level = bundle.getInt("level");	
		init();
		setlinster();
		Log.d("level",""+level);
		view = new BoadView(this,level);
		
		ly02.addView(view, 480, 530);
		loadData();
		view.invalidate();
		super.onRestart();
	}

	
	@Override
	protected void onResume() {
		loadData();
		view.invalidate();
		super.onResume();
	}

	private void init() {
		ly02 = (RelativeLayout) findViewById(R.id.layout_02);
		exit = (ImageButton) findViewById(R.id.exit);
		reset = (ImageButton) findViewById(R.id.reset);
		back = (ImageButton) findViewById(R.id.back);
		st01 = (ImageButton) findViewById(R.id.st1);
		st02 = (ImageButton) findViewById(R.id.st2);
		st03 = (ImageButton) findViewById(R.id.st3);
		st04 = (ImageButton) findViewById(R.id.st4);
		st05 = (ImageButton) findViewById(R.id.st5);
		ly02.setBackgroundResource(R.drawable.bg01);
	}

	private void setlinster() {
		exit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		reset.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				view.reset();
			}
		});
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				view.back();
			}
		});
		st01.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				change(1);
			}
		});
		st02.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				change(2);
			}
		});
		st03.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				change(3);
			}
		});
		st04.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				change(4);
			}
		});
		st05.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				change(5);
			}
		});
	}

	private void change(int key) {
		switch (key) {
		case 1:
			ly02.setBackgroundResource(R.drawable.bg01);
			break;
		case 2:
			ly02.setBackgroundResource(R.drawable.bg02);
			break;
		case 3:
			ly02.setBackgroundResource(R.drawable.bg03);
			break;
		case 4:
			ly02.setBackgroundResource(R.drawable.bg04);
			break;
		case 5:
			ly02.setBackgroundResource(R.drawable.bg05);
			break;
		default:
			Log.d("Chang() Mes", "ERROR Key");
			break;
		}
	}

	public void saveData() {
		Log.i("Activity02", "---saveData Start---");
		view.setPause(); //stop clock
		settings = getSharedPreferences("data", 0);
		Editor editor = settings.edit();
		editor.clear();
		editor.commit(); //clear SharedPreferences(SP)
		Reversi game = view.getGame(); //get the now gmae state
		PiecesType board[][] = game.getBoard();// get board
		editor.putInt("level", game.getLevel()).commit();//put the level in SP
		Log.i("Activity02", "level " + Integer.toString(game.getLevel()));
		editor.putInt("time", view.getClock()).commit();//put time in SP
		Log.i("Activity02", "Time " + Integer.toString(view.getClock()));
		for (int i = 0; i < 8; i++)//put each board's cell in SP
			for (int j = 0; j < 8; j++)
				editor.putString("BD[" + i + "][" + j + "]",
						board[i][j].toString()).commit();

		PiecesHistory history = view.getHistory();//get game's history
		State state = history.getState(); //get game's history state

		editor.putString("state_player", state.getPlayer().toString());
		Log.i("Activity02", "state_player " + state.getPlayer().toString());
		editor.putInt("stete_step_size", state.getSteps().size());
		for (int i = 0; i < state.getSteps().size(); i++) {
			Log.i("Activity02", "stete_step" + Integer.toString(i));
			editor.putInt("stete_step" + Integer.toString(i) + "_i", state
					.getSteps().get(i).i());
			editor.putInt("stete_step" + Integer.toString(i) + "_j", state
					.getSteps().get(i).i());
			editor.putString("stete_step_" + Integer.toString(i) + "_type",
					state.getSteps().get(i).getType().toString());
		}
		editor.putInt("history_state_size", history.getList().size());
		for (int i = 0; i < history.getList().size(); i++) {
			Log.i("Activity02", "history_state" + Integer.toString(i));
			State pstate = history.getList().get(i);
			editor.putInt("history_state" + Integer.toString(i) + "state_size",
					pstate.getSteps().size());
			editor.putString("history_state" + Integer.toString(i)
					+ "state_player", pstate.getPlayer().toString());
			Log.i("Activity02", "Steps size  " + Integer.toString(pstate.getSteps().size()));
			for (int j = 0; j < pstate.getSteps().size(); j++) {
				//Log.i("Activity02", "NOP: " +);
				editor.putInt("history_state" + Integer.toString(i) + "state"
						+ Integer.toString(j) + "_i", pstate.getSteps().get(j)
						.i());
				editor.putInt("history_state" + Integer.toString(i) + "state"
						+ Integer.toString(j) + "_j", pstate.getSteps().get(j)
						.j());
				editor.putString("history_state" + Integer.toString(i)
						+ "state" + Integer.toString(j) + "_type", pstate
						.getSteps().get(j).getType().toString());
			}
		}
		editor.commit();
	}

	public PiecesType getPiecesType(String str) throws Exception {
		Log.i("Activity02", "getPiecesType : " + str);
		for (PiecesType t : PiecesType.values()) {
			if (str.equals(t.toString())) {
				return t;
			}
		}
		throw new Exception("");
	}

	public boolean loadData() {
		Log.i("Activity02", "---loadData Start---");
		settings = getSharedPreferences("data", 0);
		try {

			PiecesType board[][] = new PiecesType[8][8];
			int level = settings.getInt("level", -1);
			Log.i("Activity02", "level " + Integer.toString(level));
			Reversi game = new Reversi(level);
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++) {
					//Log.i("Activity02", "BD[" + i + "][" + j + "]");
					String s = settings.getString("BD[" + i + "][" + j + "]",
							"");
					if (!s.equals("")) {

						board[i][j] = getPiecesType(s);

					} else {
						game = null;
						return false;
					}

				}
			game.setBoard(board);
			int time = settings.getInt("time", 15);
			Log.i("Activity02", "time " + Integer.toString(time));
			view.setPause();
			view.setClock(time);

			PiecesHistory history = new PiecesHistory();
			PiecesType player = this.getPiecesType(settings.getString(
					"state_player", ""));
			State state = new State(player);
			int size = settings.getInt("stete_step_size", 0);
			Log.i("Activity02", "stete_step_size " + Integer.toString(size));
			for (int i = 0; i < size; i++) {

				Log.i("Activity02", "stete_step" + Integer.toString(i));

				int _i = settings.getInt("stete_step" + Integer.toString(i)
						+ "_i", -1);
				int _j = settings.getInt("stete_step" + Integer.toString(i)
						+ "_j", -1);
				PiecesType type = this.getPiecesType(settings.getString(
						"stete_step_" + Integer.toString(i) + "_type", ""));
				PiecesPos pos = new PiecesPos(_i, _j);
				Step step = new Step(pos, type);
				state.getSteps().add(step);
			}
			history.setState(state);
			size = settings.getInt("history_state_size", 0);
			Log.i("Activity02", "history_state_size " + Integer.toString(size));
			for (int i = 0; i < size; i++) {
				int size2 = settings
						.getInt("history_state" + Integer.toString(i)
								+ "state_size", 0);
				Log.i("Activity02", "history_state" + Integer.toString(i)
						+ "state_size " + Integer.toString(size2));
				PiecesType pstate_player = this.getPiecesType(settings
						.getString("history_state" + Integer.toString(i)
								+ "state_player", ""));
				State pstate = new State(pstate_player);

				Log.i("Activity02", "history_state" + Integer.toString(i));

				for (int j = 0; j < size2; j++) {
					int _i = settings.getInt(
							"history_state" + Integer.toString(i) + "state"
									+ Integer.toString(j) + "_i", -1);
					int _j = settings.getInt(
							"history_state" + Integer.toString(i) + "state"
									+ Integer.toString(j) + "_j", -1);
					PiecesType type = this.getPiecesType(settings.getString(
							"history_state" + Integer.toString(i) + "state"
									+ Integer.toString(j) + "_type", ""));
					PiecesPos pos = new PiecesPos(_i, _j);
					Step step = new Step(pos, type);
					pstate.getSteps().add(step);
				}
				history.getList().add(pstate);
			}
			game.setHistory(history);
			view.setGame(game);
			view.setResume();
			view.invalidate();
			settings.edit().clear();
			settings.edit().commit();
			Log.i("Activity02", "LoadData Success");
			return true;

		} catch (Exception ex) {
			Log.i("Activity02", "LoadData Error");
			settings.edit().clear();
			settings.edit().commit();
			return false;
		}

	}
}
