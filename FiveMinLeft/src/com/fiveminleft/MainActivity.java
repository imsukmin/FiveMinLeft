package com.fiveminleft;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
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

	boolean[] checkState = new boolean[10];
	int[][] setTimes = new int[3][10];
	String[] boardTimer = new String[10];
	long[] finalTimer = new long[10];
	int totalTime = 0;

	CountDown timer;

	
	
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
		checkState = intent.getBooleanArrayExtra("checkState");			// confirm CheckBoxes
		setTimes[0] = intent.getExtras().getIntArray("timeState0");		// hours value
		setTimes[1] = intent.getExtras().getIntArray("timeState1");		// minutes value
		setTimes[2] = intent.getExtras().getIntArray("timeState2");
		boardTimer = intent.getExtras().getStringArray("boardTimer");
		int index = 0;
		for (int i = 0; i < 10; i++) {
			if(checkState[i]){
				setTime[index].setTextColor(Color.WHITE);
				setTime[index].setText((index+1) + ".  " + boardTimer[i] );
				finalTimer[i] = (setTimes[0][index]*3600 + setTimes[1][index]*60 + setTimes[2][index]);
				boardTimer[i] = boardTimer[i] + " || " + finalTimer[i];
				totalTime += finalTimer[i];
				index++;
			} 
		}
		
		// Start Timer
		timer = new CountDown(totalTime*1000, 1000);
		timer.start();

		spButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
		            // The toggle is enabled
					timer.cancel();
		        } else {
		            // The toggle is disabled
		        	timer.start();
		        }
			}
		});
		
		stopButton = (Button) findViewById(R.id.btnPause);
		stopButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				new AlertDialog.Builder(MainActivity.this)
			    .setTitle("Hey! 5 Minutes Left")
			    .setMessage("Are you sure you want to EXIT?")
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
			    .show();
			}
		});
	}

	public class CountDown extends CountDownTimer{

		int currentIdx=0;
		long totalSecond;
		long seconds;
		long minutes;
		long hours;
		 
        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        
        @Override
        public void onFinish() {
        	textTimer.setText("--- Time OUT!! ---");
            System.out.println("타이머가 다 되었습니다.");
			new AlertDialog.Builder(MainActivity.this)
		    .setTitle("Hey! 5 Minutes Left")
		    .setMessage("---TIME OUT!!---")
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // continue exit
		        	finish();
		        }
		     })
		    .show();
        }
 
        @Override
        public void onTick(long millisUntilFinished) {
        	
        	totalSecond = (millisUntilFinished/1000);
        	
        	if(totalSecond == totalTime - finalTimer[currentIdx]){
        		
        	}
        	
        	hours = totalSecond/3600;
        	minutes = (totalSecond%3600)/60;
        	seconds = (totalSecond%60)%60;
        	
			textTimer.setText("" + hours + ":"
			+ String.format("%02d", minutes) + ":"
			+ String.format("%02d", seconds));
//            System.out.println("타이머 : " + (millisUntilFinished/1000));
            
        }
    }
}
