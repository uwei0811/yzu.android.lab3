package hw.lab3.s1011542;

import hw.lab3.s1011542.R;
import hw.lab3.s1011542.reversi.PiecesType;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
	private ImageButton exit ;
	private ImageButton reset ;
	private ImageButton back ;
	private ImageButton st01 ;
	private ImageButton st02 ;
	private ImageButton st03 ;
	private ImageButton st04 ;
	private ImageButton st05 ;
	private BoadView view;
	private SharedPreferences settings;
	private PiecesType board[][];
	private int clk_time;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity02);
		board = new PiecesType[8][8];
		clk_time = -1;
		/*for(int i = 0 ; i < 8 ; i++)
			for(int j = 0 ; j < 8 ; j++)
				Log.d("--", ""+board[i][j]);*/
		readData();

		if(clk_time == 0)
			Log.d("-","first");
		else
			Log.d("-","sCnd::" + clk_time);
		
		init();	
		setlinster();
		
		view = new BoadView(this,board,clk_time);
		ly02.addView(view, 480, 530);
		
	}
	
	@Override
    protected void onStop(){
       super.onStop();
       saveData();
    }
	
	private void init()
	{
		ly02 = (RelativeLayout) findViewById(R.id.layout_02);
		exit = (ImageButton)findViewById(R.id.exit);
		reset = (ImageButton)findViewById(R.id.reset);
		back = (ImageButton)findViewById(R.id.back);
		st01 = (ImageButton)findViewById(R.id.st1);
		st02 = (ImageButton)findViewById(R.id.st2);
		st03 = (ImageButton)findViewById(R.id.st3);
		st04 = (ImageButton)findViewById(R.id.st4);
		st05 = (ImageButton)findViewById(R.id.st5);
		ly02.setBackgroundResource(R.drawable.bg01);
	}
	
	private void setlinster()
	{
		exit.setOnClickListener(new OnClickListener(){public void onClick(View v){finish();}});
		reset.setOnClickListener(new OnClickListener(){public void onClick(View v){view.reset();}});
		back.setOnClickListener(new OnClickListener(){public void onClick(View v){view.back();}});
		st01.setOnClickListener(new OnClickListener(){public void onClick(View v){}});
		st01.setOnClickListener(new OnClickListener(){public void onClick(View v){change(1);}});
		st02.setOnClickListener(new OnClickListener(){public void onClick(View v){change(2);}});
		st03.setOnClickListener(new OnClickListener(){public void onClick(View v){change(3);}});
		st04.setOnClickListener(new OnClickListener(){public void onClick(View v){change(4);}});
		st05.setOnClickListener(new OnClickListener(){public void onClick(View v){change(5);}});
	}
	
	private void change(int key)
	{
		switch(key)
		{
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
			Log.d("Chang() Mes","ERROR Key");
			break;
		}
	}
	
	public void readData(){
        settings = getSharedPreferences("data",0);
        clk_time = settings.getInt("time", 0);
        for(int i = 0 ; i < 8 ; i++)
        	for(int j = 0 ; j < 8 ; j++)
        		setBD(i,j,settings.getString("BD["+i+"]["+j+"]", ""));
        
        settings.edit().clear().commit();
    }
	
	private void setBD(int i , int j, String putstr)
	{
		if(putstr == "EMPTY")
			board[i][j] = PiecesType.EMPTY;
		else if(putstr == "WHITE")
			board[i][j] = PiecesType.BLACK;
		else if(putstr == "BLACK")
			board[i][j] = PiecesType.POSSIBLE;
		else if(putstr == "POSSIBLE")
			board[i][j] = PiecesType.FORBIDDEN;
		else if(putstr == "FORBIDDEN")
			board[i][j] = PiecesType.FORBIDDEN;
	}
	
    public void saveData(){
    	view.set_Thread_lock(true);
        settings = getSharedPreferences("data",0);
        PiecesType board[][] = view.get_Board();
        settings.edit().putInt("time", view.get_clk()).commit();
        for(int i = 0 ; i < 8 ; i++)
        	for(int j = 0 ; j < 8 ; j++)
        		settings.edit().putString("BD["+i+"]["+j+"]", board[i][j].toString()).commit();
    
    }
    
    
}
