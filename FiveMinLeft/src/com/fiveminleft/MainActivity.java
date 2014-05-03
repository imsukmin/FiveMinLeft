package com.fiveminleft;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	private TextView textTimer; 
	private TextView[] setTime = new TextView[10];

	private Button stopButton; 
	private ToggleButton spButton; //Start-Pause Button
	private long startTime = 0L; 
	private Handler myHandler = new Handler(); 
	long timeInMillies = 0L; 
	long timeSwap = 0L; 
	long finalTime = 0L;
	boolean[] checkState = new boolean[10];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textTimer = (TextView) findViewById(R.id.textTimer);
		spButton = (ToggleButton) findViewById(R.id.btnStartStop);
		
		setTime[0] = (TextView)findViewById(R.id.timeView1);
		setTime[1] = (TextView)findViewById(R.id.timeView2);
		setTime[2] = (TextView)findViewById(R.id.timeView3);
		setTime[3] = (TextView)findViewById(R.id.timeView4);
		setTime[4] = (TextView)findViewById(R.id.timeView5);
		setTime[5] = (TextView)findViewById(R.id.timeView6);
		setTime[6] = (TextView)findViewById(R.id.timeView7);
		setTime[7] = (TextView)findViewById(R.id.timeView8);
		setTime[8] = (TextView)findViewById(R.id.timeView9);
		setTime[9] = (TextView)findViewById(R.id.timeView10);

		for (int i = 0; i < checkState.length; i++) {
			checkState[i] = false;	
		}
		Intent intent = getIntent();
		checkState = intent.getBooleanArrayExtra("checkStare");
		for (int i = 0; i < 10; i++) {
			if(!checkState[i]){
				setTime[i].setTextColor(Color.BLACK);
				setTime[i].setText("it is NOT check");
			} else {
				setTime[i].setTextColor(Color.BLUE);
				setTime[i].setText("it is check");				
			}
			
		}
		
		spButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
		            // The toggle is enabled
					timeSwap = 0L;
					startTime = SystemClock.uptimeMillis();
					myHandler.postDelayed(updateTimerMethod, 0);
		        } else {
		            // The toggle is disabled
					timeSwap += timeInMillies;
					myHandler.removeCallbacks(updateTimerMethod);
		        }
			}
		});
		
		stopButton = (Button) findViewById(R.id.btnPause);
		stopButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				finish();
			}
		});

	}

	
	private Runnable updateTimerMethod = new Runnable() {

		public void run() {
			timeInMillies = SystemClock.uptimeMillis() - startTime;
			finalTime = timeSwap + timeInMillies;

			int seconds = (int) (finalTime / 1000);
			int minutes = seconds / 60;
			seconds = seconds % 60;
			int milliseconds = (int) (finalTime % 1000);
			textTimer.setText("" + minutes + ":"
					+ String.format("%02d", seconds) + ":"
					+ String.format("%03d", milliseconds));
			myHandler.postDelayed(this, 0);
		}

	};
}
