package com.example.googlemaptest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordresetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.passwordreset);
		Button SetPswd = (Button) findViewById(R.id.passWordReset_Btn);
		SetPswd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText SNumber = (EditText) findViewById(R.id.passwordresetSNumber);
				EditText pswd1 = (EditText) findViewById(R.id.passwordresetInput);
				EditText pswd2 = (EditText) findViewById(R.id.passwordresetInput2);
				// 비었나확인하고.
				if (!pswd1.getText().toString().isEmpty() && !pswd2.getText().toString().isEmpty()) {
					// 두개가 같은지 확인한뒤
					if (pswd1.getText().toString().equals(pswd2.getText().toString())) {
						FindpasswordThread FPT = new FindpasswordThread();
						FPT.setSnumber(SNumber.getText().toString());
						FPT.inputProcessNum(6); // 6번작업번호.
						String[] s = { pswd1.getText().toString(), pswd2.getText().toString() };
						FPT.inputText(s);
						FPT.start();

						Toast.makeText(getApplicationContext(), "비밀번호 변경에 성공하였습니다.", Toast.LENGTH_SHORT).show();
						finish();
					} else {
						Toast.makeText(getApplicationContext(), "비밀번호확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "공란이 있습니다.", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
