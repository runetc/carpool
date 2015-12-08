package com.example.googlemaptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserConditionDriver_SelectActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userconditiondriver_select);

		Intent intent = getIntent();

		final String id = intent.getExtras().getString("id");
		String grade = intent.getExtras().getString("grade");
		final String login_id = intent.getExtras().getString("login_id");

		TextView idTV = (TextView) findViewById(R.id.UserConditionDriver_Select_Id);
		TextView gradeTV = (TextView) findViewById(R.id.UserConditionDriver_Select_Grade);
		idTV.setText(id);
		gradeTV.setText(grade);

		Button AcceptBtn = (Button) findViewById(R.id.UserConditionDriver_Select_Accept);
		AcceptBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] s = { id };
				NormalThread NT = new NormalThread();
				NT.setSnumber(login_id);
				NT.inputProcessNum(20);
				NT.inputText(s);
				NT.start();

				while (!NT.getWorkComplete())
					;
				if (NT.getCheckJoinComplete()) {
					Toast.makeText(getApplicationContext(), "수락에 성공하였습니다.", Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "연결에 실패하였습니다. 다시시도해주세요.", Toast.LENGTH_SHORT).show();

				}
			}
		});

		Button RejectBtn = (Button) findViewById(R.id.UserConditionDriver_Select_Reject);

		RejectBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] s = { id };
				NormalThread NT = new NormalThread();
				NT.setSnumber(login_id);
				NT.inputProcessNum(21);
				NT.inputText(s);
				NT.start();

				while (!NT.getWorkComplete())
					;
				if (NT.getCheckJoinComplete()) {
					Toast.makeText(getApplicationContext(), "거절하였습니다.", Toast.LENGTH_SHORT).show();

					finish();
				} else {
					Toast.makeText(getApplicationContext(), "연결에 실패하였습니다. 다시시도해주세요.", Toast.LENGTH_SHORT).show();

				}
			}
		});
	}

}
