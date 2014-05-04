package com.fiveminleft;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
	static final int[][] isTime = new int[10][3];
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

		for (int i = 0; i < setTime.length; i++) {
			final int index = i;
			setTime[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
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
					// TODO Auto-generated method stub
					if (checkBox[index].isChecked()) {
						intentG.putExtra("slotIndex", index);
						setTime[index].setEnabled(true);
						showDialog();
					} else if (!(checkBox[index].isChecked())) {
						setTime[index].setEnabled(false);
					}
				}
			});
			;

		}

		Button startButton = (Button) findViewById(R.id.btnStartAtSetting);
		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
		Button exitButton = (Button) findViewById(R.id.btnExit);
		exitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		Log.i("value is",""+newVal);
	}

	public void showDialog() {
		
		final int[] timer = new int[3];

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
		final NumberPicker min = (NumberPicker) getDialog
				.findViewById(R.id.setMinute);
		min.setMaxValue(59); // max value 59
		min.setMinValue(0); // min value 0
		min.setWrapSelectorWheel(true);
		min.setOnValueChangedListener(this);
		final NumberPicker sec = (NumberPicker) getDialog
				.findViewById(R.id.setSecond);
		sec.setMaxValue(59); // max value 59
		sec.setMinValue(1); // min value 1
		sec.setWrapSelectorWheel(true);
		sec.setOnValueChangedListener(this);
		btnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String boardTime;
				int slotIndex = intentG.getExtras().getInt("slotIndex");
				Log.e("" + slotIndex,"" + slotIndex);
				timer[0] = hour.getValue(); 
				timer[1] = min.getValue();
				timer[2] = sec.getValue();
				boardTime = "Hour [" + timer[0]  + "] Minute [" + timer[1] + "] Second [" + timer[2] + "]";
				
				setTime[slotIndex].setText(boardTime.toCharArray(), 0, boardTime.length());;
				boardTimer[slotIndex] = boardTime; 
				isTime[slotIndex][0] = timer[0];
				isTime[slotIndex][1] = timer[1];
				isTime[slotIndex][2] = timer[2];
				makeToast("Setting Complete!");
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
