package com.example.googlemaptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserinfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfo);

		Intent intent = getIntent();

		String[] SA = intent.getExtras().getStringArray("SA");

		TextView id = (TextView) findViewById(R.id.UserInfo_Id);
		TextView name = (TextView) findViewById(R.id.UserInfo_Name);
		TextView Email = (TextView) findViewById(R.id.UserInfo_Email);
		TextView Pnumber = (TextView) findViewById(R.id.UserInfo_Pnumber);
		TextView DriverId = (TextView) findViewById(R.id.UserInfo_DriverId);
		TextView DriverCarClass = (TextView) findViewById(R.id.UserInfo_DriverCarClass);
		TextView DriverCarNumber = (TextView) findViewById(R.id.UserInfo_DriverCarNumber);
		TextView DriverCarColor = (TextView) findViewById(R.id.UserInfo_DriverCarColor);

		id.setText(SA[0]);
		name.setText(SA[1]);
		Email.setText(SA[2]);
		Pnumber.setText(SA[3]);

		int i = 4;

		if (!SA[i].equals("EOT")) {
			DriverId.setText(SA[i]);
			i++;

			DriverCarClass.setText(SA[i]);
			i++;
			DriverCarNumber.setText(SA[i]);
			i++;
			DriverCarColor.setText(SA[i]);
			i++;
		}
		Button user_infoBtn = (Button) findViewById(R.id.userinfo_passwordreset);
		user_infoBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(UserinfoActivity.this, PasswordresetActivity.class);
				startActivity(i);
			}
		});
	}
}