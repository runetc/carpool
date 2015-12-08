package com.example.googlemaptest;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class UserConditionTaxiActivity extends Activity {
	private ListView mListView = null;
	private UserConditionTaxi_ListSelect mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userconditiontaxi);
		
		Intent intent = getIntent();

		String[] SA = intent.getExtras().getStringArray("SA");
		final String login_id = intent.getExtras().getString("login_id");

		mListView = (ListView) findViewById(R.id.UserConditionTaxi_LV);

		mAdapter = new UserConditionTaxi_ListSelect(this);
		mListView.setAdapter(mAdapter);
		
		final String[] idList = new String[500];
		String id;
		String startX;
		String startY;
		String arriveX;
		String arriveY;
		String time;
		String person;
		String startAddress;
		String arriveAddress;

		int i = 0;
		int j = 0;
		while (!SA[i].equals("EOT")) {
			id = SA[i];
			idList[j] = SA[i];
			i++;
			j++;
			startX = SA[i];
			i++;
			startY = SA[i];
			i++;
			arriveX = SA[i];
			i++;
			arriveY = SA[i];
			i++;
			time = SA[i];
			i++;
			person = SA[i];
			i++;
			startAddress = findAddress(Double.parseDouble(startX), Double.parseDouble(startY));
			arriveAddress = findAddress(Double.parseDouble(arriveX), Double.parseDouble(arriveY));
			mAdapter.addItem(id, startAddress, arriveAddress, time, person);
		}
		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View clickedView, int position, long id) {
				Intent i = new Intent(UserConditionTaxiActivity.this, UserConditionTaxi_SelectActivity.class);
				startActivity(i);
			}
		});
	}
	private String findAddress(double lng, double lat) {
		StringBuffer bf = new StringBuffer();
		Geocoder geocoder = new Geocoder(this, Locale.KOREA);
		List<Address> address;
		String currentLocationAddress;
		try {
			if (geocoder != null) {
				// ����° �μ��� �ִ������ε� �ϳ��� ���Ϲ޵��� �����ߴ�
				address = geocoder.getFromLocation(lat, lng, 1);
				// ������ �����ͷ� �ּҰ� ���ϵ� �����Ͱ� ������
				if (address != null && address.size() > 0) {
					// �ּ�
					currentLocationAddress = address.get(0).getAddressLine(0).toString();

					// ������ �ּ� ������ (����/�浵 ���� ����)
					bf.append(currentLocationAddress);
				}
			}

		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), "�ּ���� ����", Toast.LENGTH_LONG).show();

			e.printStackTrace();
		}
		return bf.toString();
	}

}
