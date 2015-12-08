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
				// �й��� �̸��Ϻ���ִ��� Ȯ��.
				if (!SNumber.getText().toString().isEmpty() && !Email.getText().toString().isEmpty()) {
					FindpasswordThread FPT = new FindpasswordThread();
					String[] s = { Email.getText().toString() };
					FPT.setSnumber(SNumber.getText().toString());
					FPT.inputProcessNum(4);
					FPT.inputText(s);
					FPT.start();
					
					Toast.makeText(getApplicationContext(), "������ȣ�� �߼۵Ǿ����ϴ�. ������ȣ�� Ȯ�����ּ���", Toast.LENGTH_SHORT).show();

				} else {
					Toast.makeText(getApplicationContext(), "�й� Ȥ�� �̸����� �Էµ��� �ʾҽ��ϴ�.", Toast.LENGTH_SHORT).show();
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
				// ������ȣ &�̸��� �����˻�.
				if (!pswdConfirm.getText().toString().isEmpty() && !Email.getText().toString().isEmpty()) {
					NormalThread FPT = new NormalThread();
					// �̸��ϰ� ������ȣ�� ����.
					String[] s = { pswdConfirm.getText().toString() };
					FPT.setSnumber(SNumber.getText().toString());
					FPT.inputProcessNum(5);
					FPT.inputText(s);
					FPT.start();
					
					while (!FPT.getWorkComplete())
						;

					if (FPT.getCheckJoinComplete()) {
						Toast.makeText(getApplicationContext(), "�����Ϸ�!", Toast.LENGTH_SHORT).show();
						Intent i = new Intent(FindpasswordActivity.this, PasswordresetActivity.class);
						startActivity(i);
					} else {
						Toast.makeText(getApplicationContext(), "������ȣ�� Ʋ���ϴ�!", Toast.LENGTH_SHORT).show();
						
					}

				} else {
					Toast.makeText(getApplicationContext(), "�̸��� Ȥ�� ������ȣ�� �Էµ��� �ʾҽ��ϴ�.", Toast.LENGTH_SHORT).show();
				}

			}
		});

	}
}
