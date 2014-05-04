package com.fiveminleft;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class SettingActivity extends Activity implements
		NumberPicker.OnValueChangeListener {

	static final int SET_TIME_DIALOG = 0;

	static int index;
	static Toast toast;
	static Dialog getDialog;
	static final Intent intentG = new Intent();
	static final boolean[] isChecked = new boolean[10];
	static final int[][] isTime = new int[3][10];
	static final CheckBox[] checkBox = new CheckBox[10];
	static final EditText[] setTime = new EditText[10];
	static final String[] boardTimer = new String[10];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_activity);

		checkBox[0] = (CheckBox) findViewById(R.id.CheckBox00);
		checkBox[1] = (CheckBox) findViewById(R.id.CheckBox01);
		checkBox[2] = (CheckBox) findViewById(R.id.CheckBox02);
		checkBox[3] = (CheckBox) findViewById(R.id.CheckBox03);
		checkBox[4] = (CheckBox) findViewById(R.id.CheckBox04);
		checkBox[5] = (CheckBox) findViewById(R.id.CheckBox05);
		checkBox[6] = (CheckBox) findViewById(R.id.CheckBox06);
		checkBox[7] = (CheckBox) findViewById(R.id.CheckBox07);
		checkBox[8] = (CheckBox) findViewById(R.id.CheckBox08);
		checkBox[9] = (CheckBox) findViewById(R.id.CheckBox09);

		setTime[0] = (EditText) findViewById(R.id.setTime00);
		setTime[1] = (EditText) findViewById(R.id.setTime01);
		setTime[2] = (EditText) findViewById(R.id.setTime02);
		setTime[3] = (EditText) findViewById(R.id.setTime03);
		setTime[4] = (EditText) findViewById(R.id.setTime04);
		setTime[5] = (EditText) findViewById(R.id.setTime05);
		setTime[6] = (EditText) findViewById(R.id.setTime06);
		setTime[7] = (EditText) findViewById(R.id.setTime07);
		setTime[8] = (EditText) findViewById(R.id.setTime08);
		setTime[9] = (EditText) findViewById(R.id.setTime09);

		final Button startButton = (Button) findViewById(R.id.btnStartAtSetting);
		final Button exitButton = (Button) findViewById(R.id.btnExit);
		
		for (int i = 0; i < setTime.length; i++) {
			final int index = i;
			setTime[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					intentG.putExtra("slotNumber", index);
					showDialog();
				}
			});
		}
		for (int i = 0; i < checkBox.length; i++) {
			final int index = i;
			checkBox[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (checkBox[index].isChecked()) {
						intentG.putExtra("slotIndex", index);
						setTime[index].setEnabled(true);
						showDialog();
						startButton.setEnabled(true);
					} else if (!(checkBox[index].isChecked())) {
						setTime[index].setEnabled(false);
						for (int j = 0; j < checkBox.length; j++) {
							if(checkBox[j].isChecked()){
								break;
							}
							if(checkBox.length == j+1){
								startButton.setEnabled(false);								
							}
							
						}
					}
				}
			});
			;

		}

		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < isChecked.length; i++) {
					isChecked[i] = checkBox[i].isChecked();
				}
				// Send Data to MainActvity
				Intent intent = new Intent(SettingActivity.this,
						MainActivity.class);
				intent.putExtra("checkState", isChecked);
				intent.putExtra("timeState0", isTime[0]);
				intent.putExtra("timeState1", isTime[1]);
				intent.putExtra("timeState2", isTime[2]);
				intent.putExtra("boardTimer", boardTimer);
				startActivity(intent);
			}
		});
		
		exitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(SettingActivity.this)
			    .setTitle("Hey! 5 Minutes Left")
			    .setMessage("EXIT application?")
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
//			    .setIcon(android.R.drawable.ic_dialog_alert)
			    .show();
			}
		});

	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//		Log.i("value is",""+newVal);
	}

	public void showDialog() {
		
		final int[] timer = new int[3];
		int slotIdx = intentG.getExtras().getInt("slotIndex");
		
		final Dialog getDialog = new Dialog(SettingActivity.this);
		getDialog.setContentView(R.layout.timepicker);
		Button btnOK = (Button) getDialog.findViewById(R.id.dialogOK);
		Button btnCancel = (Button) getDialog.findViewById(R.id.dialogCancel);
		
		final NumberPicker hour = (NumberPicker) getDialog
				.findViewById(R.id.setHour);
		hour.setMaxValue(23); // max value 23
		hour.setMinValue(0); // min value 0
		hour.setWrapSelectorWheel(true);
		hour.setOnValueChangedListener(this);
		if(hour.getValue() == 0)
		hour.setValue(isTime[0][slotIdx]);
		
		final NumberPicker min = (NumberPicker) getDialog
				.findViewById(R.id.setMinute);
		min.setMaxValue(59); // max value 59
		min.setMinValue(0); // min value 0
		min.setWrapSelectorWheel(true);
		min.setOnValueChangedListener(this);
		min.setValue(isTime[1][slotIdx]);
		
		final NumberPicker sec = (NumberPicker) getDialog
				.findViewById(R.id.setSecond);
		sec.setMaxValue(59); // max value 59
		sec.setMinValue(1); // min value 1
		sec.setWrapSelectorWheel(true);
		sec.setOnValueChangedListener(this);
		if(sec.getValue()==0){
			sec.setValue(1);
		} else {
			sec.setValue(isTime[2][slotIdx]);
		}
		btnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String boardTime;
				int slotIndex = intentG.getExtras().getInt("slotIndex");
				Log.e("" + slotIndex,"" + slotIndex);
				timer[0] = hour.getValue(); 
				timer[1] = min.getValue();
				timer[2] = sec.getValue();
				boardTime = "" + timer[0]  + " : " + timer[1] + " : " + timer[2] + 
						" || total sec :" + (timer[0]*3600 + timer[1]*60 + timer[2]);
				setTime[slotIndex].setText(boardTime.toCharArray(), 0, boardTime.length());;
				boardTimer[slotIndex] = boardTime; 
				isTime[0][slotIndex] = timer[0];
				isTime[1][slotIndex] = timer[1];
				isTime[2][slotIndex] = timer[2];
				makeToast("NO. " + (slotIndex+1) + "Setting Success!");
				getDialog.dismiss();
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				makeToast("Setting Canceled!");
				getDialog.dismiss(); // dismiss the dialog
			}
		});
		getDialog.show();

	}

	public void makeToast(String msg) {
		toast = Toast
				.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
		toast.show();
	}

}
