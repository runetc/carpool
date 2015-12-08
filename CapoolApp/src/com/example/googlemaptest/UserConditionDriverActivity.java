package com.example.googlemaptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UserConditionDriverActivity extends Activity {
	private ListView mListView = null;
	private UserConditionDriver_ListSelect mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userconditiondriver);

		Intent intent = getIntent();

		String[] SA = intent.getExtras().getStringArray("SA");
		final String login_id = intent.getExtras().getString("login_id");

		mListView = (ListView) findViewById(R.id.UserConditionDriver_LV);

		mAdapter = new UserConditionDriver_ListSelect(this);
		mListView.setAdapter(mAdapter);
		TextView CurPerTV = (TextView) findViewById(R.id.UserConditionDriver_CurPerson);

		String Snumber;
		String Grade;
		final String[] idList = new String[500];
		final String[] gradeList = new String[500];

		int i = 1;
		int j = 0;
		if (!SA[0].equals("EOT")) {
			CurPerTV.setText(SA[0]);
			while (!SA[i].equals("EOT")) {
				Snumber = SA[i];
				idList[j] = SA[i];
				i++;

				Grade = SA[i];
				gradeList[j] = SA[i];
				i++;
				j++;
				mAdapter.addItem(Snumber, Grade);
			}
		}
		if (i == 0) {
			Toast.makeText(getApplicationContext(), "매칭 결과가 없습니다.", Toast.LENGTH_SHORT).show();

		}

		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View clickedView, int position, long id) {
				Intent i = new Intent(UserConditionDriverActivity.this, UserConditionDriver_SelectActivity.class);
				i.putExtra("id", idList[position]);
				i.putExtra("grade", gradeList[position]);
				i.putExtra("login_id", login_id);
				startActivity(i);
			}
		});
		Button ViewBtn = (Button) findViewById(R.id.UserConditionDriver_ViewBtn);
		ViewBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				UserConditionDriver_BoarderViewThread UCT = new UserConditionDriver_BoarderViewThread();
				UCT.setSnumber(login_id);
				UCT.inputProcessNum(19);
				UCT.start();

				String[] SA = UCT.getInputMsg();
				while (!UCT.getWorkComplete())
					;
				if (UCT.getCheckJoinComplete()) {
					Intent i = new Intent(UserConditionDriverActivity.this,
							UserConditionDriver_BoarderViewActivity.class);
					i.putExtra("SA", SA);
					i.putExtra("login_id", login_id);
					startActivity(i);
				}
			}
		});
	}

}
