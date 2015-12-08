package com.example.googlemaptest;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UserConditionBoarder_SelectItemActivity extends Activity {

	private ListView mListView = null;
	private UserConditionBoarder_SelectItem_ListSelect mAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userconditionboarder_select);
		mListView = (ListView) findViewById(R.id.UserConditionBoarder_Select_LV);

		mAdapter = new UserConditionBoarder_SelectItem_ListSelect(this);
		mListView.setAdapter(mAdapter);
		
		Intent intent = getIntent();
		String[] SA = intent.getStringArrayExtra("SA");
		boolean accept = intent.getExtras().getBoolean("accept");

		TextView startTv = (TextView) findViewById(R.id.UserConditionBoarder_Select_Start);
		TextView arriveTv = (TextView) findViewById(R.id.UserConditionBoarder_Select_Arrive);
		TextView timeTv = (TextView) findViewById(R.id.UserConditionBoarder_Select_Time);
		TextView payTv = (TextView) findViewById(R.id.UserConditionBoarder_Select_Pay);
		TextView carTv = (TextView) findViewById(R.id.UserConditionBoarder_Select_Car);
		TextView personTv = (TextView) findViewById(R.id.UserConditionBoarder_Select_Person);
		TextView gradeTv = (TextView) findViewById(R.id.UserConditionBoarder_Select_Grade);
		TextView acceptTv = (TextView) findViewById(R.id.UserConditionBoarder_Select_Accept);
		
		String startX;
		String startY;
		String arriveX;
		String arriveY;
		
		int i = 0;
		
		startX = SA[i++];
		startY = SA[i++];
		arriveX = SA[i++];
		arriveY = SA[i++];
		startTv.setText(findAddress(Double.parseDouble(startX), Double.parseDouble(startY)));
		arriveTv.setText(findAddress(Double.parseDouble(arriveX), Double.parseDouble(arriveY)));
		timeTv.setText(SA[i++]);
		payTv.setText(SA[i++]);
		carTv.setText(SA[i++]);
		personTv.setText(SA[i++]);
		gradeTv.setText(SA[i++]);
		
		if(accept){
			acceptTv.setText("�����Ǿ����ϴ�.");
		}else
		{
			acceptTv.setText("������� ���Դϴ�.");
		}
		while(!SA[i].equals("EOT")){
			
			mAdapter.addItem(SA[i++], SA[i++]);
		}
		
		

		
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