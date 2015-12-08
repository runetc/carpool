package com.example.googlemaptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UserConditionMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userconditionmenu);

		Intent intent = getIntent();
		final String login_id = intent.getExtras().getString("login_id");
		Button TaxiBtn = (Button) findViewById(R.id.UserConditionMenu_Taxi);
		TaxiBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UserConditionMenuThread UCMT = new UserConditionMenuThread();

				UCMT.setSnumber(login_id);
				UCMT.inputProcessNum(17);
				UCMT.start();

				String[] SA = UCMT.getInputMsg();
				while (!UCMT.getWorkComplete())
					;
				if (UCMT.getCheckJoinComplete()) {
					Intent i = new Intent(UserConditionMenuActivity.this,
							UserConditionTaxi_Select_DecisionListActivity.class);
					i.putExtra("SA", SA);
					i.putExtra("login_id", login_id);
					startActivity(i);
				} else {
					Toast.makeText(getApplicationContext(), "조회가 실패하였습니다. 다시 시도 해주세요", Toast.LENGTH_SHORT).show();
				}

			}
		});
		Button DriverBtn = (Button) findViewById(R.id.UserConditionMenu_Driver);
		DriverBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				UserConditionDriverThread UCMT = new UserConditionDriverThread();

				UCMT.setSnumber(login_id);
				UCMT.inputProcessNum(18);
				UCMT.start();

				String[] SA = UCMT.getInputMsg();
				while (!UCMT.getWorkComplete())
					;
				if (UCMT.getCheckJoinComplete()) {
					Intent i = new Intent(UserConditionMenuActivity.this, UserConditionDriverActivity.class);
					i.putExtra("SA", SA);
					i.putExtra("login_id", login_id);
					startActivity(i);
				} else {
					Toast.makeText(getApplicationContext(), "조회가 실패하였습니다. 다시 시도 해주세요", Toast.LENGTH_SHORT).show();
				}
			}
		});
		Button BoarderBtn = (Button) findViewById(R.id.UserConditionMenu_Boarder);
		BoarderBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				UserConditionMenuBoarderThread ucmbt = new UserConditionMenuBoarderThread();

				ucmbt.setSnumber(login_id);
				ucmbt.inputProcessNum(22);
				ucmbt.start();

				while (!ucmbt.getWorkComplete())
					;

				String[] SA = ucmbt.getInputMsg();
				if (ucmbt.getCheckJoinComplete()) {

					Intent i = new Intent(UserConditionMenuActivity.this,
							UserConditionBoarder_SelectItemActivity.class);
					i.putExtra("SA", SA);
					i.putExtra("accept", ucmbt.getAccept());
					i.putExtra("login_id", login_id);
					startActivity(i);
				} else {
					Toast.makeText(getApplicationContext(), "조회가 실패하였습니다. 다시 시도 해주세요", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}
}