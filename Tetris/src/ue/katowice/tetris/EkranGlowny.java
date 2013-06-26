package ue.katowice.tetris;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

public class EkranGlowny extends Activity {
	Gra _gra;
	Kontroler _kontroler;
	Timer _timer;
	Timer _drawTimer;
	int _predkoscMs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ekran_glowny);
		
		// http://stackoverflow.com/questions/2902640/android-get-the-screen-resolution-pixels-as-integer-values
		DisplayMetrics displayMetrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		int screenWidth = displayMetrics.widthPixels;
		
		_kontroler = new Kontroler();
		_timer = new Timer("tetrisTimer");
		_drawTimer = new Timer("drawTimer");
		_gra = new Gra(getApplicationContext(), this, _kontroler, _drawTimer, screenWidth);
		_kontroler.SetGra(_gra);
		_predkoscMs = 400;
		
		Button btnGraj = (Button)findViewById(R.id.bGraj);
		btnGraj.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				_timer.schedule(_kontroler, 0, _predkoscMs);
				setContentView(_gra);
				_drawTimer.schedule(new TimerTask() {
					@Override
					public void run() {
						if(_gra.CanDraw())
							_gra.postInvalidate();
					}
				}, 0, _predkoscMs);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ekran_glowny, menu);
		return true;
	}

}
