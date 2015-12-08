package com.example.googlemaptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class UserEstimateActivity extends Activity {
	private ListView mListView = null;
	private UserEstimate_ListSelect mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userestimateview);

		
		Intent intent = getIntent();

		String[] SA = intent.getExtras().getStringArray("SA");
		final String login_id = intent.getExtras().getString("login_id");
		
		String count;
		String time;
		String id;
		final String[] timeList = new String[50];
		final String[] idList = new String[50];
		int i = 0;
		int j = 0;
		while (!SA[i].equals("EOT")) {
			count = String.valueOf(j);
			
			time = SA[i];
			timeList[j] = SA[i];
			
			i++;
			id = SA[i];
			idList[j] = SA[i];
			i++;
			j++;
			mAdapter.addItem(count, time, id);
		}
		
		mListView = (ListView) findViewById(R.id.userestimateview_LV);

		mAdapter = new UserEstimate_ListSelect(this);
		mListView.setAdapter(mAdapter);

		
		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View clickedView, int position, long id) {
				Intent i = new Intent(UserEstimateActivity.this, UserEstimateListSelectActivity.class);
				i.putExtra("Snumber", idList[position]);
				i.putExtra("Time", timeList[position]);
				i.putExtra("login_id", login_id);
				
				startActivity(i);
			}
		});
	}

}
