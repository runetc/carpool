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
			acceptTv.setText("수락되었습니다.");
		}else
		{
			acceptTv.setText("수락대기 중입니다.");
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
				// 세번째 인수는 최대결과값인데 하나만 리턴받도록 설정했다
				address = geocoder.getFromLocation(lat, lng, 1);
				// 설정한 데이터로 주소가 리턴된 데이터가 있으면
				if (address != null && address.size() > 0) {
					// 주소
					currentLocationAddress = address.get(0).getAddressLine(0).toString();

					// 전송할 주소 데이터 (위도/경도 포함 편집)
					bf.append(currentLocationAddress);
				}
			}

		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), "주소취득 실패", Toast.LENGTH_LONG).show();

			e.printStackTrace();
		}

		return bf.toString();
	}
}