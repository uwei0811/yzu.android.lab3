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

public class Activity03 extends Activity {
	private RelativeLayout ly03; 
	private Bundle bundle;
	private Intent intent2;
	private ImageButton btlvl01;
	private ImageButton btlvl02;
	private ImageButton btlvl03;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity03);
		ly03 = (RelativeLayout) findViewById(R.id.layout_03);
		ly03.setBackgroundResource(R.drawable.bgimage02);
		intent2 = this.getIntent();
		bundle = new Bundle();
		btlvl01 = (ImageButton)findViewById(R.id.level1);
		btlvl02 = (ImageButton)findViewById(R.id.level2);
		btlvl03 = (ImageButton)findViewById(R.id.level3);
		
		btlvl01.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				bundle.putInt("level", 1);
				intent2.putExtras(bundle);
				setResult(Activity.RESULT_OK, intent2);
				finish();
		}});
		
		btlvl02.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				bundle.putInt("level", 2);
				intent2.putExtras(bundle);
				setResult(Activity.RESULT_OK, intent2);
				finish();
		}});
		
		btlvl03.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				bundle.putInt("level", 3);
				intent2.putExtras(bundle);
				setResult(Activity.RESULT_OK, intent2);
				finish();
		}});
	}
    
}
