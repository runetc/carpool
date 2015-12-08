package com.example.googlemaptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main_menuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);

		Intent intent = getIntent();
		final String login_id = intent.getExtras().getString("login_id");

		Button taxiBtn = (Button) findViewById(R.id.main_menu_taxi);
		taxiBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Main_menuActivity.this, TaxiActivity.class);
				i.putExtra("login_id", login_id);
				startActivity(i);
			}
		});

		Button carBtn = (Button) findViewById(R.id.main_menu_car);
		carBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Main_menuActivity.this, Car_menuActivity.class);
				i.putExtra("login_id", login_id);
				startActivity(i);
			}
		});

		Button boardBtn = (Button) findViewById(R.id.main_menu_board);
		boardBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Main_menuThread MMT = new Main_menuThread();

				MMT.setSnumber(login_id);
				MMT.inputProcessNum(12);

				MMT.start();

				String[] SA = MMT.getInputMsg();
				while (!MMT.getWorkComplete())
					;
				if (MMT.getCheckJoinComplete()) {
					Intent i = new Intent(Main_menuActivity.this, BoardMain_MainActivity.class);
					i.putExtra("SA", SA);
					i.putExtra("login_id", login_id);
					startActivity(i);
				} else {
					Toast.makeText(getApplicationContext(), "조회가 실패하였습니다. 다시 시도 해주세요", Toast.LENGTH_SHORT).show();
				}

			}
		});

		Button statusBtn = (Button) findViewById(R.id.main_menu_status);
		statusBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Main_menuActivity.this, Status_MenuActivity.class);
				i.putExtra("login_id", login_id);
				startActivity(i);
			}
		});
	}
}
