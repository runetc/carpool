package com.example.googlemaptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class UserConditionBoarderActivity extends Activity {
	private ListView mListView = null;
	private UserConditionBoarder_ListSelect mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userconditionboarder);

		mListView = (ListView) findViewById(R.id.UserConditionBoarder_LV);

		mAdapter = new UserConditionBoarder_ListSelect(this);
		mListView.setAdapter(mAdapter);

		mAdapter.addItem("1","�ݿ�����", "���̿�", "1515151515", "1000��");
		mAdapter.addItem("2","�ݿ�����", "���̿�", "1515151515", "1000��");
		mAdapter.addItem("3","�ݿ�����", "���̿�", "1515151515", "1000��");

		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View clickedView, int position, long id) {
				Intent i = new Intent(UserConditionBoarderActivity.this, UserConditionBoarder_SelectItemActivity.class);
				startActivity(i);
			}
		});
	}

}
