package com.example.googlemaptest;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Board_AddReplyActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.board_addreply);

		Intent intent = getIntent();

		final String login_id = intent.getExtras().getString("login_id");
		final EditText title = (EditText) findViewById(R.id.board_AddReply_Title);
		final EditText contents = (EditText) findViewById(R.id.board_AddReply_Contents);
		Button addBtn = (Button) findViewById(R.id.board_AddReply_AddBtn);
		Button cancelBtn = (Button) findViewById(R.id.board_AddReply_CancelBtn);

		
		addBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String[] s = { title.getText().toString(), contents.getText().toString() };
				NormalThread NT = new NormalThread();
				NT.setSnumber(login_id);
				NT.inputProcessNum(13);
				NT.inputText(s);
				NT.start();

				while (!NT.getWorkComplete())
					;
				if (NT.getCheckJoinComplete()) {
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "글쓰기가 실패하였습니다. 다시 시도 해주세요", Toast.LENGTH_SHORT).show();
				}
			
			}
		});

		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}
	
}
