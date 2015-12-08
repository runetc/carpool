
package com.example.googlemaptest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class UserEstimateListSelectActivity extends Activity implements AdapterView.OnItemSelectedListener {

	String[] items = { "선택하세요","1", "2", "3", "4", "5" };

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userestimatelistselect);
		
		Intent intent = getIntent();

		final String Snumber = intent.getExtras().getString("Snumber");
		final String Time = intent.getExtras().getString("Time");
		final String login_id = intent.getExtras().getString("login_id");

		Spinner spin = (Spinner) findViewById(R.id.userestimatelistselect_Point);
	
		spin.setOnItemSelectedListener(this);

		ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);

		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spin.setAdapter(aa);
		
		Button AddBtn = (Button) findViewById(R.id.userestimatelistselect_Add);
		AddBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				finish();
			}
		});
	}

	public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
		
		((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

		// Of cause, This source is abailable for index number more than 0.
	}

	public void onNothingSelected(AdapterView<?> parent) {
		
	}
}
