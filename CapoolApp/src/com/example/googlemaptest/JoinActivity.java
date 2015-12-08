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

		// ������ȣ��ư����
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

					// ��ٸ��� Ʈ����
					while (!jt.getWorkComplete())
						;
					Toast.makeText(getApplicationContext(), "�б� �̸��Ϸ� ������ȣ�� Ȯ�����ּ���.", Toast.LENGTH_SHORT).show();

				} else
					Toast.makeText(getApplicationContext(), "��ĭ�� ������ �Է��� �ٽ� Ȯ�����ּ���.", Toast.LENGTH_SHORT).show();
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

				// ���⼭ ������ȣ�� ������ ��������.
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
						Toast.makeText(getApplicationContext(), "������ ����. ������ ���ϵ帳�ϴ�.", Toast.LENGTH_SHORT).show();
						finish();
					} else {
						Toast.makeText(getApplicationContext(), "������ȣ�� Ʋ���ϴ�. ������ȣ�� Ȯ�����ּ���.", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "�� ĭ�� �ֽ��ϴ�. �Է�â�� �ٽ� Ȯ�����ּ���.", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	// �ڷᰡ ������ true��ȯ or ������false
	public boolean checkData(String s) {
		return !s.isEmpty();
	}
}
