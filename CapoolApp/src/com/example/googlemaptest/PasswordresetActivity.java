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
				// �����Ȯ���ϰ�.
				if (!pswd1.getText().toString().isEmpty() && !pswd2.getText().toString().isEmpty()) {
					// �ΰ��� ������ Ȯ���ѵ�
					if (pswd1.getText().toString().equals(pswd2.getText().toString())) {
						FindpasswordThread FPT = new FindpasswordThread();
						FPT.setSnumber(SNumber.getText().toString());
						FPT.inputProcessNum(6); // 6���۾���ȣ.
						String[] s = { pswd1.getText().toString(), pswd2.getText().toString() };
						FPT.inputText(s);
						FPT.start();

						Toast.makeText(getApplicationContext(), "��й�ȣ ���濡 �����Ͽ����ϴ�.", Toast.LENGTH_SHORT).show();
						finish();
					} else {
						Toast.makeText(getApplicationContext(), "��й�ȣȮ���� ��ġ���� �ʽ��ϴ�.", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "������ �ֽ��ϴ�.", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
