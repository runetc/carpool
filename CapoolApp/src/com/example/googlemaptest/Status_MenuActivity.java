package com.example.googlemaptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Status_MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status_menu);

		Intent intent = getIntent();
		final String login_id = intent.getExtras().getString("login_id");
		
		Button user_infoBtn = (Button) findViewById(R.id.status_menu_user_info);
		user_infoBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Status_MenuThread SMT = new Status_MenuThread();
				SMT.setSnumber(login_id);
				SMT.inputProcessNum(16);
				SMT.start();
				
				String[] SA = SMT.getInputMsg();
				while (!SMT.getWorkComplete())
					;
				if (SMT.getCheckJoinComplete()) {
					Intent i = new Intent(Status_MenuActivity.this, UserinfoActivity.class);
					i.putExtra("SA", SA);
					i.putExtra("login_id", login_id);
					startActivity(i);
				} else {
					Toast.makeText(getApplicationContext(), "조회가 실패하였습니다. 다시 시도 해주세요", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		Button user_conditionBtn = (Button) findViewById(R.id.status_present_condition);
		user_conditionBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Status_MenuActivity.this, UserConditionMenuActivity.class);
				i.putExtra("login_id", login_id);
				startActivity(i);
			}
		});
		Button user_estimateBtn = (Button) findViewById(R.id.status_menu_appraisal);
		user_estimateBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				UserEstimate_Thread UET = new UserEstimate_Thread();
				UET.setSnumber(login_id);
				UET.inputProcessNum(23);
				
				String[] SA = UET.getInputMsg();
				Intent i = new Intent(Status_MenuActivity.this, UserEstimateActivity.class);
				i.putExtra("SA", SA);
				i.putExtra("login_id", login_id);
				startActivity(i);
			}
		});
		Button user_viewscoreBtn = (Button) findViewById(R.id.status_menu_view_appraisal);
		user_viewscoreBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Status_MenuActivity.this, UserEstimate_Viewscore_Activity.class);
				i.putExtra("login_id", login_id);
				startActivity(i);
			}
		});
	}
}
