package com.example.googlemaptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class UserConditionTaxi_Select_DecisionListActivity extends Activity {
	private ListView mListView = null;
	private UserConditionTaxi_Select_DecisionList_ListSelect mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userconditiontaxi_select_decisionlist);

		Intent intent = getIntent();

		String[] SA = intent.getExtras().getStringArray("SA");
		final String login_id = intent.getExtras().getString("login_id");
		
		mListView = (ListView) findViewById(R.id.UserConditionTaxi_Select_DecisionList_LV);

		mAdapter = new UserConditionTaxi_Select_DecisionList_ListSelect(this);
		mListView.setAdapter(mAdapter);
		int i = 0;
		
		String Pnumber;
		while (!SA[i].equals("EOT")) {
			Pnumber = SA[i];
			i++;
			String numStr = String.valueOf(i);
			mAdapter.addItem(numStr,Pnumber);
			
		}
		if(i ==0)
		{
			Toast.makeText(getApplicationContext(), "매칭 결과가 없습니다.", Toast.LENGTH_SHORT).show();
			
		}
	}

}
