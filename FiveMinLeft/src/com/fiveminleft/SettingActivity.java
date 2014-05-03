package com.fiveminleft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class SettingActivity extends Activity {
	
	final boolean[] isChecked = new boolean[10];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_activity);
		
		Button startButton = (Button)findViewById(R.id.btnStartAtSetting);
		final CheckBox[] cb = new CheckBox[10];
		cb[0] = (CheckBox)findViewById(R.id.CheckBox00);
		cb[1] = (CheckBox)findViewById(R.id.CheckBox01);
		cb[2] = (CheckBox)findViewById(R.id.CheckBox02);
		cb[3] = (CheckBox)findViewById(R.id.CheckBox03);
		cb[4] = (CheckBox)findViewById(R.id.CheckBox04);
		cb[5] = (CheckBox)findViewById(R.id.CheckBox05);
		cb[6] = (CheckBox)findViewById(R.id.CheckBox06);
		cb[7] = (CheckBox)findViewById(R.id.CheckBox07);
		cb[8] = (CheckBox)findViewById(R.id.CheckBox08);
		cb[9] = (CheckBox)findViewById(R.id.CheckBox09);
		
		startButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub 
				for (int i = 0; i < isChecked.length; i++) {
					isChecked[i] = cb[i].isChecked();
				}
				Intent intent = new Intent(SettingActivity.this, MainActivity.class);
				intent.putExtra("checkStare", isChecked);
				startActivity(intent);				
			}
		});
		
	}
}
