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
import android.widget.TextView;
import android.widget.Toast;

public class Boarding_SearchActivity extends Activity {
	private ListView mListView = null;
	private Boarding_Search_ListSelect mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boarding_search);
		mListView = (ListView) findViewById(R.id.Boarding_Search_LV);
		mAdapter = new Boarding_Search_ListSelect(this);
		mListView.setAdapter(mAdapter);

		String id;
		final String[] idList = new String[50];
		String inputStartLong;
		String inputStartLat;
		String inputArriveLong;
		String inputArriveLat;
		String startAddress;
		String arriveAddress;
		String time;
		String pay;

		Intent intent = getIntent();

		String[] SA = intent.getExtras().getStringArray("SA");
		final String login_id = intent.getExtras().getString("login_id");

		int i = 0;
		int j = 0;
		while (!SA[i].equals("EOT")) {
			id = SA[i];
			idList[j] = SA[i];
			i++;
			j++;
			inputStartLong = SA[i];
			i++;
			inputStartLat = SA[i];
			i++;
			inputArriveLong = SA[i];
			i++;
			inputArriveLat = SA[i];
			i++;
			startAddress = findAddress(Double.parseDouble(inputStartLong), Double.parseDouble(inputStartLat));
			arriveAddress = findAddress(Double.parseDouble(inputArriveLong), Double.parseDouble(inputArriveLat));
			time = SA[i];
			i++;
			pay = SA[i];
			i++;
			mAdapter.addItem(id, startAddress, arriveAddress, time, pay);
		}

		// String startLat = intent.getExtras().getString("startLat");
		// String arriveLong = intent.getExtras().getString("arriveLong");
		// String arriveLat = intent.getExtras().getString("arriveLat");

		/*
		 * Boarding_SearchThread BSThread = new Boarding_SearchThread();
		 * 
		 * 
		 * // 이메일과 인증번호만 보냄. String[] s = { startLong, startLat, arriveLong,
		 * arriveLat }; BSThread.inputProcessNum(9); BSThread.inputText(s);
		 * BSThread.start();
		 */

		// String[] inputMsg = BSThread.getInputMsg();

		/*
		 * id = startLong[0]; inputStartLong = startLong[0];
		 * 
		 * inputStartLat = startLong[0];
		 * 
		 * inputArriveLong = startLong[0];
		 * 
		 * inputArriveLat = startLong[0];
		 * 
		 * time = startLong[0];
		 * 
		 * pay = startLong[0];
		 * 
		 * mAdapter.addItem(id, inputStartLong, inputArriveLong, time, pay);
		 * mAdapter.addItem("1", "2","1", "2","1");
		 * mListView.setAdapter(mAdapter); /*while (!BSThread.getWorkComplete())
		 * ; Toast.makeText(getApplicationContext(),
		 * Boolean.toString(BSThread.getCheckJoinComplete()),
		 * Toast.LENGTH_SHORT).show();
		 */
		/*
		 * while (!inputMsg[count].equals("EOT")) {
		 * 
		 * id = inputMsg[count]; count++; inputStartLong = inputMsg[count];
		 * count++; inputStartLat = inputMsg[count]; count++; inputArriveLong =
		 * inputMsg[count]; count++; inputArriveLat = inputMsg[count]; count++;
		 * time = inputMsg[count]; count++; pay = inputMsg[count]; count++;
		 * startAddress =
		 * inputStartLong;//findAddress(Double.parseDouble(inputStartLong),
		 * Double.parseDouble(inputStartLat)); arriveAddress =
		 * inputArriveLong;//findAddress(Double.parseDouble(inputArriveLong),
		 * Double.parseDouble(inputArriveLat)); mAdapter.addItem(id,
		 * startAddress, arriveAddress, time, pay); }
		 * 
		 * 
		 * while (!BSThread.getWorkComplete()) ;
		 * 
		 * 
		 * if (BSThread.getCheckJoinComplete()) {
		 * 
		 * Toast.makeText(getApplicationContext(), "잠시만 기다려주세요.",
		 * Toast.LENGTH_SHORT).show();
		 * 
		 * } else { Toast.makeText(getApplicationContext(),
		 * "조회가 실패하였습니다. 다시 시도 해주세요", Toast.LENGTH_SHORT).show(); }
		 */

		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View clickedView, int position, long id) {
				String[] s = { idList[position] };
				Boarding_SearchThread BST = new Boarding_SearchThread();
				BST.setSnumber(login_id);
				BST.inputProcessNum(10);
				BST.inputText(s);
				BST.start();

				String[] SA = BST.getInputMsg();
				while (!BST.getWorkComplete())
					;
				if (BST.getCheckJoinComplete()) {
					Intent i = new Intent(Boarding_SearchActivity.this, Boarding_Search_SelectListActivity.class);
					i.putExtra("SA", SA);
					i.putExtra("login_id", login_id);
					startActivity(i);
				} else {
					Toast.makeText(getApplicationContext(), "조회가 실패하였습니다. 다시 시도 해주세요", Toast.LENGTH_SHORT).show();
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
