package com.example.googlemaptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class UserEstimate_Viewscore_Activity extends Activity {
	private ListView mListView = null;
	private UserEstimate_Viewscore_ListSelect mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userestimate_viewscore);

		mListView = (ListView) findViewById(R.id.userestimate_viewscore_LV);

		mAdapter = new UserEstimate_Viewscore_ListSelect(this);
		mListView.setAdapter(mAdapter);
		mAdapter.addItem("1", "5", "��۳�����Դϴ�");
		mAdapter.addItem("2", "6", "��۳�����Դϴ�");
		mAdapter.addItem("3", "7", "��۳�����Դϴ�");

	}

}
