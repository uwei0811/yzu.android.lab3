package hw.lab3.s1011542;


import hw.lab3.s1011542.R;
import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;

public class Music {
	private SoundPool alert_music;
	private Activity music_activity;
	private int alertId;
	
	Music(Activity MainActivity){
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
}
