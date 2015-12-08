package com.example.googlemaptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Car_menuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_menu);

		Intent intent = getIntent();
		final String login_id = intent.getExtras().getString("login_id");
		
		Button drivingBtn = (Button) findViewById(R.id.car_menu_driving);
		drivingBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Car_menuActivity.this, DrivingActivity.class);
				i.putExtra("login_id", login_id);
				startActivity(i);
			}
		});
		
		Button boardBtn = (Button) findViewById(R.id.car_menu_boarding);
		boardBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Car_menuActivity.this, BoardingActivity.class);
				i.putExtra("login_id", login_id);
				startActivity(i);
			}
		});
	}
}
