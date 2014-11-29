package hw.lab3.s1011542;

import hw.lab3.s1011542.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	private ImageButton Start;
	private ImageButton Hard;
	private ImageButton Record;
	private ImageButton Exit;
	private Intent intent;
	Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
		
		Start.setOnClickListener(new OnClickListener(){public void onClick(View v){GoActivity();}});
	}

	private void init()
	{
		Start = (ImageButton)findViewById(R.id.imgbtn_start);
		Hard = (ImageButton)findViewById(R.id.imgbtn_hard);
		Record = (ImageButton)findViewById(R.id.imgbtn_record);
		Exit = (ImageButton)findViewById(R.id.imgbtn_exit);
	}
	
	private void GoActivity()
	{
		try{
	    	  intent = new Intent();
	    	  intent.setClass(MainActivity.this, Activity02.class);
	    	  Log.d("Intent Log","MainActivity to ScendActivity");
	    	  startActivity(intent);
		}catch(Exception e){
			Log.d("Intent Log","Error of MainActivity to ScendActivity");
	    }
	}
}
