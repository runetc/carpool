package com.example.googlemaptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		Button joinBtn = (Button) findViewById(R.id.login_join);

		joinBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this, JoinActivity.class);
				startActivity(i);
			}
		});

		Button loginBtn = (Button) findViewById(R.id.login);

		loginBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText idEditText = (EditText) findViewById(R.id.login_id);
				String login_id = idEditText.getText().toString();
				EditText pswdEditText = (EditText) findViewById(R.id.login_password);
				String login_pswd = pswdEditText.getText().toString();
				if (checkData(login_id) && checkData(login_pswd)) {
					// 여기부터
					NormalThread LT = new NormalThread();
					LT.setSnumber(login_id);
					LT.inputProcessNum(3);
					String[] s = { login_pswd };
					LT.inputText(s);
					LT.start();

					while (!LT.getWorkComplete())
						;

					if (LT.getCheckJoinComplete()) {
						Toast.makeText(getApplicationContext(), "로그인완료. ", Toast.LENGTH_SHORT).show();
						// 여기까지
						Intent i = new Intent(LoginActivity.this, Main_menuActivity.class);
						i.putExtra("login_id", login_id);
						startActivity(i);
					} else {
						Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호가 일치하지 않습니다. ", Toast.LENGTH_SHORT).show();
					}
				} else
					Toast.makeText(getApplicationContext(), "아이디 패스워드를 확인해주세요.", Toast.LENGTH_SHORT).show();
			}
		});

		Button pwresetBtn = (Button) findViewById(R.id.login_findPassword);
		pwresetBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this, FindpasswordActivity.class);
				startActivity(i);
			}
		});

	}

	public boolean checkData(String s) {
		return !s.isEmpty();

	}
}
