package com.example.googlemaptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UserConditionDriver_BoarderViewActivity extends Activity {
	private ListView mListView = null;
	private UserConditionDriver_ListSelect mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userconditiondriver_boarderview);

		Intent intent = getIntent();

		String[] SA = intent.getExtras().getStringArray("SA");

		mListView = (ListView) findViewById(R.id.UserConditionDriver_BoarderView_LV);

		mAdapter = new UserConditionDriver_ListSelect(this);
		mListView.setAdapter(mAdapter);
		
		
		String Snumber;
		String Grade;
	
		
		int i = 0;
		while (!SA[i].equals("EOT")) {
			Snumber = SA[i];
			i++;

			Grade = SA[i];
			i++;

			mAdapter.addItem(Snumber, Grade);
		}
		if (i == 0) {
			Toast.makeText(getApplicationContext(), "매칭 결과가 없습니다.", Toast.LENGTH_SHORT).show();

		}

		
		
	}

}
