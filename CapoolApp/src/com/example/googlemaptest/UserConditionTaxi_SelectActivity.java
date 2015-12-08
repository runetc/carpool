package com.example.googlemaptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserConditionTaxi_SelectActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userconditiontaxi_select);

		Button DecisionBtn = (Button) findViewById(R.id.UserConditionTaxi_Select_DecisionBtn);
		DecisionBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(UserConditionTaxi_SelectActivity.this,
						UserConditionTaxi_Select_DecisionListActivity.class);
				startActivity(i);
			}
		});
		Button CancelBtn = (Button) findViewById(R.id.UserConditionTaxi_Select_CancelBtn);
		CancelBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(UserConditionTaxi_SelectActivity.this, UserConditionTaxiActivity.class);
				startActivity(i);
			}
		});

	}

}
