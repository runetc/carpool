package com.example.googlemaptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FindpasswordActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findpassword);

		Button RequestMailBtn = (Button) findViewById(R.id.findpassword_RequestMailConfirmNumber);
		RequestMailBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText Email = (EditText) findViewById(R.id.findpassword_Email);
				EditText SNumber = (EditText) findViewById(R.id.findpassword_InputSNumber);
				// 학번과 이메일비어있는지 확인.
				if (!SNumber.getText().toString().isEmpty() && !Email.getText().toString().isEmpty()) {
					FindpasswordThread FPT = new FindpasswordThread();
					String[] s = { Email.getText().toString() };
					FPT.setSnumber(SNumber.getText().toString());
					FPT.inputProcessNum(4);
					FPT.inputText(s);
					FPT.start();
					
					Toast.makeText(getApplicationContext(), "인증번호가 발송되었습니다. 인증번호를 확인해주세요", Toast.LENGTH_SHORT).show();

				} else {
					Toast.makeText(getApplicationContext(), "학번 혹은 이메일이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		Button fpcBtn = (Button) findViewById(R.id.findpassword_Confirm);
		fpcBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText Email = (EditText) findViewById(R.id.findpassword_Email);
				EditText SNumber = (EditText) findViewById(R.id.findpassword_InputSNumber);
				EditText pswdConfirm = (EditText) findViewById(R.id.findpassword_InputPConfirmNumber);
				// 인증번호 &이메일 공란검사.
				if (!pswdConfirm.getText().toString().isEmpty() && !Email.getText().toString().isEmpty()) {
					NormalThread FPT = new NormalThread();
					// 이메일과 인증번호만 보냄.
					String[] s = { pswdConfirm.getText().toString() };
					FPT.setSnumber(SNumber.getText().toString());
					FPT.inputProcessNum(5);
					FPT.inputText(s);
					FPT.start();
					
					while (!FPT.getWorkComplete())
						;

					if (FPT.getCheckJoinComplete()) {
						Toast.makeText(getApplicationContext(), "인증완료!", Toast.LENGTH_SHORT).show();
						Intent i = new Intent(FindpasswordActivity.this, PasswordresetActivity.class);
						startActivity(i);
					} else {
						Toast.makeText(getApplicationContext(), "인증번호가 틀립니다!", Toast.LENGTH_SHORT).show();
						
					}

				} else {
					Toast.makeText(getApplicationContext(), "이메일 혹은 인증번호가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
				}

			}
		});

	}
}
