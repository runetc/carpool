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
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class BoardReading_MainActivity extends Activity {
	private ListView mListView = null;
	private ScrollView mScrollView = null;
	private BoardReading_ListSelect mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boardreading);

		Intent intent = getIntent();

		String[] SA = intent.getExtras().getStringArray("SA");
		final String login_id = intent.getExtras().getString("login_id");

		mScrollView = (ScrollView) findViewById(R.id.boardReading_SV);
		mListView = (ListView) findViewById(R.id.boardReading_ReplyView);

		mAdapter = new BoardReading_ListSelect(this);

		final String postId;
		TextView postWriter = (TextView) findViewById(R.id.boardReading_Writer);
		TextView postWritingDate = (TextView) findViewById(R.id.boardReading_WritingDate);
		TextView postTitle = (TextView) findViewById(R.id.boardReading_Title);
		TextView postContents = (TextView) findViewById(R.id.boardReading_Contents);

		postId = SA[0];
		postWriter.setText(SA[1]);
		postWritingDate.setText(SA[2]);
		postTitle.setText(SA[3]);
		postContents.setText(SA[4]);
		mListView.setAdapter(mAdapter);
		int i = 5;

		String writer;
		String date;
		String contents;
		while (!SA[i].equals("EOT")) {
			writer = SA[i];
			i++;
			date = SA[i];
			i++;
			contents = SA[i];
			i++;

			mAdapter.addItem(writer, date, contents);
		}

		final EditText replyContents = (EditText) findViewById(R.id.boardReading_CreateReply);
		Button replyBtn = (Button) findViewById(R.id.boardReading_ReplyButton);

		replyBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] s = { postId, replyContents.getText().toString() };
				NormalThread NT = new NormalThread();
				NT.setSnumber(login_id);
				NT.inputProcessNum(15);
				NT.inputText(s);
				NT.start();
				
				while (!NT.getWorkComplete())
					;
				if (NT.getCheckJoinComplete()){
					Toast.makeText(getApplicationContext(), "댓글을 성공적으로 등록하였습니다.", Toast.LENGTH_SHORT).show();
					
				}else {
					Toast.makeText(getApplicationContext(), "댓글 달기가 실패하였습니다. 다시 시도 해주세요", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

}
