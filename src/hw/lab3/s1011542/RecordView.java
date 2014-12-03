package hw.lab3.s1011542;

import java.util.Date;

import hw.lab3.s1011542.reversi.PiecesHistory;
import hw.lab3.s1011542.reversi.PiecesPos;
import hw.lab3.s1011542.reversi.PiecesType;
import hw.lab3.s1011542.reversi.Reversi;
import hw.lab3.s1011542.reversi.ReversiGameState;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

public class RecordView extends View {

	private int record[];
	public RecordView(Activity context,int x[]) {
		super(context);
		record = x;
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Paint p = new Paint();  

		p.setColor(Color.BLACK);

		p.setStyle(Paint.Style.STROKE);

		p.setStrokeWidth(5);
		
		p.setTextSize(40);
		
		for(int i = 0 ; i < record.length && i < 10 ;i++)
			canvas.drawText("TOP "+(i+1)+" :  "+record[i], 60, 50+i*60, p);
		
	}

}
