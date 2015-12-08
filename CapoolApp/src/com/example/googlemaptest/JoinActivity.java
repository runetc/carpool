package com.example.googlemaptest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JoinActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join);

		// 인증번호버튼생성
		Button sendCertBtn = (Button) findViewById(R.id.join_RequestConfirmNumber);
		sendCertBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText SNumber = (EditText) findViewById(R.id.join_SNumber);
				EditText pswd = (EditText) findViewById(R.id.join_Password);
				EditText eMail = (EditText) findViewById(R.id.join_Mail);
				EditText Name = (EditText) findViewById(R.id.join_Name);
				EditText PNumber = (EditText) findViewById(R.id.join_PNumber);
				if (checkData(SNumber.getText().toString()) && checkData(pswd.getText().toString())
						&& checkData(eMail.getText().toString()) && checkData(Name.getText().toString())
						&& checkData(PNumber.getText().toString())) {

					String[] s = { eMail.getText().toString() };
					JoinThread jt = new JoinThread();
					jt.setSnumber(SNumber.getText().toString());
					jt.inputProcessNum(1);
					jt.inputText(s);
					jt.start();

					// 기다리는 트리거
					while (!jt.getWorkComplete())
						;
					Toast.makeText(getApplicationContext(), "학교 이메일로 인증번호를 확인해주세요.", Toast.LENGTH_SHORT).show();

				} else
					Toast.makeText(getApplicationContext(), "빈칸이 없는지 입력을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
			}
		});
		Button JoinBtn = (Button) findViewById(R.id.join_JoinBtn);
		JoinBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText SNumber = (EditText) findViewById(R.id.join_SNumber);
				EditText pswd = (EditText) findViewById(R.id.join_Password);
				EditText eMail = (EditText) findViewById(R.id.join_Mail);
				EditText Name = (EditText) findViewById(R.id.join_Name);
				EditText PNumber = (EditText) findViewById(R.id.join_PNumber);
				EditText ConfirmNumber = (EditText) findViewById(R.id.join_InputMConfirmNumber);

				// 여기서 인증번호를 서버로 보내고나서.
				if (checkData(SNumber.getText().toString()) && checkData(pswd.getText().toString())
						&& checkData(eMail.getText().toString()) && checkData(Name.getText().toString())
						&& checkData(PNumber.getText().toString()) && checkData(ConfirmNumber.getText().toString())) {
					JoinThread JoinFinalDataThread = new JoinThread();
					String[] JoinData = { eMail.getText().toString(), Name.getText().toString(),
							pswd.getText().toString(), PNumber.getText().toString(),
							ConfirmNumber.getText().toString() };
					JoinFinalDataThread.setSnumber(SNumber.getText().toString());
					JoinFinalDataThread.inputProcessNum(2);
					JoinFinalDataThread.inputText(JoinData);
					JoinFinalDataThread.start();

					while (!JoinFinalDataThread.getWorkComplete())
						;
					if (JoinFinalDataThread.getCheckJoinComplete()) {
						Toast.makeText(getApplicationContext(), "인증에 성공. 가입을 축하드립니다.", Toast.LENGTH_SHORT).show();
						finish();
					} else {
						Toast.makeText(getApplicationContext(), "인증번호가 틀립니다. 인증번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "빈 칸이 있습니다. 입력창을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	// 자료가 있으면 true반환 or 없으면false
	public boolean checkData(String s) {
		return !s.isEmpty();
	}
}
