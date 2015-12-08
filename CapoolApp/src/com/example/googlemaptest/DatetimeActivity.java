package com.example.googlemaptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class DatetimeActivity extends Activity {
	DatePicker dp;
	TimePicker tp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datetime);

		Button okBtn = (Button) findViewById(R.id.datetime_ok);
		dp = (DatePicker) findViewById(R.id.datetime_date);
		tp = (TimePicker) findViewById(R.id.datetime_time);
		tp.setIs24HourView(true);

		okBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("year", dp.getYear());
				i.putExtra("month", dp.getMonth());
				i.putExtra("day", dp.getDayOfMonth());
				i.putExtra("hour", tp.getCurrentHour());
				i.putExtra("minute", tp.getCurrentMinute());
				setResult(RESULT_OK, i);
				finish();
			}
		});
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent();
		i.putExtra("year", dp.getYear());
		i.putExtra("month", dp.getMonth());
		i.putExtra("day", dp.getDayOfMonth());
		i.putExtra("hour", tp.getCurrentHour());
		i.putExtra("minute", tp.getCurrentMinute());
		setResult(RESULT_OK, i);
		finish();
	}
}
