package hw.lab3.s1011542;


import hw.lab3.s1011542.R;
import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class Music {
	private SoundPool alert_music;
	private Activity music_activity;
	private int alertId;
	
	Music(Activity MainActivity){
		this.destory();
		music_activity = MainActivity;
		initSound();
	}
	
	private void initSound()
    {
		alert_music = new SoundPool(1,AudioManager.STREAM_MUSIC,5);
		alertId = alert_music.load(music_activity,R.raw.alert, 1);
    }
	
	public void start(){
		alert_music.play(alertId, 1.0F, 1.0F, 0, 0, 1.0F);
    }
	
	public void destory() {
		Log.i("Music", "Destory");
		if(alert_music != null) {
			alert_music.unload(alertId);
			alert_music.release();
			alert_music = null; 
		}
		if(music_activity != null) {
			music_activity = null;
		}
		
	}
}
