package hw.lab3.s1011542;

import hw.lab3.s1011542.R;
import hw.lab3.s1011542.reversi.PiecesType;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class Activity04 extends Activity {
	private RelativeLayout ly04; 
		
	//資料庫名
	private String db_name = "MVP_DB";
	
	//表名
	private String table_name = "MVP";
	
	private SQLiteDatabase db;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity04);
		ly04 = (RelativeLayout) findViewById(R.id.layout_04);
		ly04.setBackgroundResource(R.drawable.bgimage02);

		//輔助類名
		DataSQL helper = new DataSQL(Activity04.this, db_name);
 
		db = helper.getReadableDatabase();

		int x[] = myNote();
		for(int i = 0 ; i < x.length;i++)
			Log.d("x[][]",""+x[i]);
		
		RecordView view = new RecordView(this,x);
		
		ly04.addView(view, 480, 800);
	}
	
	public int[] myNote(){
	  
		Cursor cursor = db.rawQuery("select Record from MVP ORDER BY MVP.Record DESC", null);
		//用陣列存資料
		int[] sNote = new int[cursor.getCount()];
	  
		int rows_num = cursor.getCount();//取得資料表列數

		if(rows_num != 0) {
			cursor.moveToFirst();   //將指標移至第一筆資料
			for(int i=0; i<rows_num; i++) {
				int strCr = cursor.getInt(0);
			sNote[i]=strCr;
		    
			cursor.moveToNext();//將指標移至下一筆資料
			}
		}
		
		cursor.close(); //關閉Cursor
		//dbHelper.close();//關閉資料庫，釋放記憶體，還需使用時不要關閉
	  
	 
		return sNote;
	}
}
