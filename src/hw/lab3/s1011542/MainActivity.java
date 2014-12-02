package hw.lab3.s1011542;

import hw.lab3.s1011542.R;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	private ImageButton Start;
	private ImageButton Level;
	private ImageButton Record;
	private ImageButton Exit;
	private Intent intent;
	private int level;
	Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
		
		Start.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
			GoActivity();
		}});
		Level.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
			intent = null;
			intent = new Intent();
		    intent.setClass(MainActivity.this, Activity03.class);
			startActivityForResult(intent, 0);
		}});
		
		Record.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
			intent = null;
			intent = new Intent();
		    intent.setClass(MainActivity.this, Activity04.class);
			startActivity(intent);
		}});
		
	}

	private void init()
	{
		Start = (ImageButton)findViewById(R.id.imgbtn_start);
		Level = (ImageButton)findViewById(R.id.imgbtn_hard);
		Record = (ImageButton)findViewById(R.id.imgbtn_record);
		Exit = (ImageButton)findViewById(R.id.imgbtn_exit);
		level = 1;
	}
	
	private void GoActivity()
	{
		try{
			  intent = null;
	    	  intent = new Intent();
	    	  intent.setClass(MainActivity.this, Activity02.class);
	    	  Bundle bundle = new Bundle();
	    	  bundle.putInt("level", level);
	    	  intent.putExtras(bundle);
	    	  Log.d("Intent Log","MainActivity to ScendActivity");
	    	  startActivity(intent);
		}catch(Exception e){
			Log.d("Intent Log","Error of MainActivity to ScendActivity");
	    }
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
 
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			level = bundle.getInt("level");
		}
	}
}
