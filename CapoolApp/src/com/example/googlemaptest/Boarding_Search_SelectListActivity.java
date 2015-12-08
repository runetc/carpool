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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Boarding_Search_SelectListActivity extends Activity {

	private ListView mListView = null;
	private Boarding_Search_Select_ListSelect mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boarding_search_select);
		mListView = (ListView) findViewById(R.id.Boarding_Search_Select_LV);

		mAdapter = new Boarding_Search_Select_ListSelect(this);
		mListView.setAdapter(mAdapter);

		Intent intent = getIntent();

		String[] SA = intent.getExtras().getStringArray("SA");
		final String login_id = intent.getExtras().getString("login_id");

		final String demandId = SA[0];
		String startX = SA[1];
		String startY = SA[2];
		String arriveX = SA[3];
		String arriveY = SA[4];

		TextView start = (TextView) findViewById(R.id.Boarding_Search_Select_Start);
		start.setText(findAddress(Double.parseDouble(startX), Double.parseDouble(startY)));
		TextView arrive = (TextView) findViewById(R.id.Boarding_Search_Select_Arrive);
		arrive.setText(findAddress(Double.parseDouble(arriveX), Double.parseDouble(arriveY)));
		TextView time = (TextView) findViewById(R.id.Boarding_Search_Select_Time);
		time.setText(SA[5]);
		TextView pay = (TextView) findViewById(R.id.Boarding_Search_Select_Pay);
		pay.setText(SA[6]);
		TextView car = (TextView) findViewById(R.id.Boarding_Search_Select_Car);
		car.setText(SA[7]);
		TextView person = (TextView) findViewById(R.id.Boarding_Search_Select_Person);
		person.setText(SA[8]);
		TextView grade = (TextView) findViewById(R.id.Boarding_Search_Select_Grade);
		grade.setText(SA[9]);

		
		int i = 10;
		int j = 1;
		int date;
		String rating;
		String context;
		while (!SA[i].equals("EOT")) {
			date = j;
			j++;
			rating = SA[i];
			i++;
			context = SA[i];
			i++;

			mAdapter.addItem(String.valueOf(date), rating, context);
		}
		
		
		Button joinBtn = (Button) findViewById(R.id.Boarding_Search_Select_JoinBtn);
		joinBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NormalThread NT = new NormalThread();
				String[] s = {demandId};
				NT.setSnumber(login_id);
				NT.inputProcessNum(11);
				NT.inputText(s);
				NT.start();
				
				while (!NT.getWorkComplete())
					;

				if (NT.getCheckJoinComplete()){
					Toast.makeText(getApplicationContext(), "��Ī�� ��û�Ǿ����ϴ�. ��Ī����� ������ > ��û��Ȳ���� Ȯ�����ּ���. ",
							Toast.LENGTH_SHORT).show();
					finish();
				}else {
					Toast.makeText(getApplicationContext(), "��Ī�� �����Ͽ����ϴ�. �ٽýõ����ּ���", Toast.LENGTH_SHORT).show();
				}
				
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
