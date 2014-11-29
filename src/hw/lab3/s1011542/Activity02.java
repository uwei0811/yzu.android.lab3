package hw.lab3.s1011542;

import hw.lab3.s1011542.R;
import android.app.Activity;
import android.content.Intent;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity02);
		
		init();
		setlinster();
		
		view = new BoadView(this);
		ly02.addView(view, 480, 530);
		
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
}
