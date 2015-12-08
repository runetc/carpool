package com.example.googlemaptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class BoardMain_MainActivity extends Activity {
	private ListView mListView = null;
	private BoardMain_ListSelect mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boardmain);

		Intent intent = getIntent();
		String[] SA = intent.getExtras().getStringArray("SA");
		final String login_id = intent.getExtras().getString("login_id");

		mListView = (ListView) findViewById(R.id.boardMain_LV);

		mAdapter = new BoardMain_ListSelect(this);
		mListView.setAdapter(mAdapter);

		final String[] idList = new String[500];
		String id;
		String title;
		String writer;
		String date;

		int i = 0;
		int j = 0;
		while (!SA[i].equals("EOT")) {
			id = SA[i];
			idList[j] = SA[i];
			i++;
			j++;
			title = SA[i];
			i++;
			writer = SA[i];
			i++;
			date = SA[i];
			i++;
			mAdapter.addItem(id, title, writer, date);
		}

		Button addButton = (Button) findViewById(R.id.boardMain_WriteBtn);
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(BoardMain_MainActivity.this, Board_AddReplyActivity.class);
				i.putExtra("login_id", login_id);
				startActivity(i);
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View clickedView, int position, long id) {
				BoardMain_Thread BMT = new BoardMain_Thread();
				String[] s = { idList[position] };
				BMT.setSnumber(login_id);
				BMT.inputProcessNum(14);
				BMT.inputText(s);
				BMT.start();

				String[] SA = BMT.getInputMsg();
				while (!BMT.getWorkComplete())
					;
				if (BMT.getCheckJoinComplete()) {
					Intent i = new Intent(BoardMain_MainActivity.this, BoardReading_MainActivity.class);
					i.putExtra("SA", SA);
					i.putExtra("login_id", login_id);
					startActivity(i);
				} else {
					Toast.makeText(getApplicationContext(), "조회가 실패하였습니다. 다시 시도 해주세요", Toast.LENGTH_SHORT).show();
				}

				
			}
		});
	}

}
