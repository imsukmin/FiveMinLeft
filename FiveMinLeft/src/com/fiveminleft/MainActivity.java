package com.fiveminleft;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
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
	int[][] setTimes = new int[10][3];
	String[] boardTimer = new String[10];
	long[] finalTimer = new long[10];
	CountDownTimer timer = null;
	
	
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
		checkState = intent.getBooleanArrayExtra("checkState");
		setTimes[0] = intent.getExtras().getIntArray("timeState0");
		setTimes[1] = intent.getExtras().getIntArray("timeState1");
		setTimes[2] = intent.getExtras().getIntArray("timeState2");
		boardTimer = intent.getExtras().getStringArray("boardTimer");
		for (int i = 0, index = 0; i < 10; i++) {
			if(checkState[i]){
				setTime[index].setTextColor(Color.WHITE);
				setTime[index].setText((index+1) + ".  " + boardTimer[index] );
				finalTimer[i] = (setTimes[index][0]*3600 + setTimes[index][1]*60 + setTimes[index][2]);
				index++;
			} 
		}
		
		spButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
		            // The toggle is enabled
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
				
				new AlertDialog.Builder(MainActivity.this)
			    .setTitle("Hey! 5 Minutes Left")
			    .setMessage("Are you sure you want to EXIT timer?")
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue exit
			        	finish();
			        }
			     })
			    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // do nothing
			        }
			     })
			    .setIcon(android.R.drawable.ic_dialog_alert)
			    .show();
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
