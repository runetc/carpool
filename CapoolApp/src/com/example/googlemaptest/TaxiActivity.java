package com.example.googlemaptest;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TaxiActivity extends FragmentActivity implements OnMapClickListener {
	Button matchingBtn;
	GoogleMap mGoogleMap;
	Marker startMarker;
	Marker arriveMarker;
	MarkerOptions startMarkerOption;
	MarkerOptions arriveMarkerOption;

	int plag; // 1이면 출발버튼클릭, 2면 도착버튼클릭
	int startPlag = 0; // 0이면 없음 1이면 존재.
	int arrivePlag = 0; // start와 마찬가지.
	double startLong = 0;
	double startLat = 0;
	double arriveLong = 0;
	double arriveLat = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.taxi);
		Intent intent = getIntent();

		final String login_id = intent.getExtras().getString("login_id");
		init();

		Button StartBtn = (Button) findViewById(R.id.taxi_StartBtn);
		StartBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				plag = 1;

			}
		});
		Button ArriveBtn = (Button) findViewById(R.id.taxi_ArriveBtn);
		ArriveBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				plag = 2;

			}
		});

		final Button matchingBtn = (Button) findViewById(R.id.taxi_matching);
		matchingBtn.setText("매칭");
		matchingBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				String msg;

				// 각각의 좌표가 설정되었나 확인한다.
				if (startLong != 0 && arriveLong != 0) {

					NormalThread TaxiThread = new NormalThread();
					TaxiThread.inputProcessNum(7);
					String[] s = { Double.toString(startLong), Double.toString(startLat), Double.toString(arriveLong),
							Double.toString(arriveLat) };
					TaxiThread.setSnumber(login_id);
					TaxiThread.inputText(s);
					TaxiThread.start();

					while (!TaxiThread.getWorkComplete())
						;

					if (TaxiThread.getCheckJoinComplete()) {
						Toast.makeText(getApplicationContext(), "매칭이 신청되었습니다. 매칭결과는 내상태 > 신청현황에서 확인해주세요. ",
								Toast.LENGTH_SHORT).show();
						finish();
					} else {
						Toast.makeText(getApplicationContext(), "매칭이 실패하였습니다. 다시시도해주세요", Toast.LENGTH_SHORT).show();
						
					}
				} else
					Toast.makeText(getApplicationContext(), "출, 도착지가 제대로 설정되지 않았습니다.", Toast.LENGTH_SHORT).show();
			}
		});
		/*
		 * matchingBtn = (Button) findViewById(R.id.taxi_matching);
		 * matchingBtn.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { String msg;
		 * 
		 * if (matchingBtn.getText() == "매칭") { msg = "취소"; } else { msg = "매칭";
		 * }
		 * 
		 * matchingBtn.setText(msg); } });
		 * 
		 */
	}

	private void init() {

		Intent getI = getIntent();

		String title = getI.getStringExtra("marker test 1");
		String coordinates[] = { "36.144269", "128.392625" };
		double lat = Double.parseDouble(coordinates[0]);
		double lng = Double.parseDouble(coordinates[1]);

		LatLng position = new LatLng(lat, lng);
		GooglePlayServicesUtil.isGooglePlayServicesAvailable(TaxiActivity.this);

		mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

		// 항공사진 변경
		mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		// 터치이벤트 설정
		mGoogleMap.setOnMapClickListener(this);

		// 맵 위치이동.
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

		// 마커 클릭 리스너
		mGoogleMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			public boolean onMarkerClick(Marker marker) {
				String text = "[마커 클릭 이벤트] latitude =" + marker.getPosition().latitude + ", longitude ="
						+ marker.getPosition().longitude;
				Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
				return false;
			}
		});

		// 클릭이벤트
		mGoogleMap.setOnMapClickListener(new OnMapClickListener() {

			public void onMapClick(LatLng point) {
				if (plag == 1) {
					if (startPlag == 0) {

						startMarkerOption = new MarkerOptions();
						startMarkerOption.position(point);
						startMarkerOption.title("출발지");
						startMarkerOption.draggable(false);
						startMarker = mGoogleMap.addMarker(startMarkerOption);
						startMarker.showInfoWindow();
						startPlag = 1;
						startLong = startMarkerOption.position(point).getPosition().longitude;
						startLat = startMarkerOption.position(point).getPosition().latitude;

					} else if (startPlag == 1) {

						startMarker.remove();
						startMarkerOption = new MarkerOptions();
						startMarkerOption.position(point);
						startMarkerOption.title("출발지");
						startMarkerOption.draggable(false);
						startMarker = mGoogleMap.addMarker(startMarkerOption);
						startMarker.showInfoWindow();
						startLong = startMarkerOption.position(point).getPosition().longitude;
						startLat = startMarkerOption.position(point).getPosition().latitude;
					}

				} else if (plag == 2) {

					if (arrivePlag == 0) {

						arriveMarkerOption = new MarkerOptions();
						arriveMarkerOption.position(point);
						arriveMarkerOption.title("도착지");
						arriveMarkerOption.draggable(false);
						arriveMarker = mGoogleMap.addMarker(arriveMarkerOption);
						arriveMarker.showInfoWindow();
						arrivePlag = 1;
						arriveLong = arriveMarkerOption.position(point).getPosition().longitude;
						arriveLat = arriveMarkerOption.position(point).getPosition().latitude;

					} else if (arrivePlag == 1) {

						arriveMarker.remove();
						arriveMarkerOption = new MarkerOptions();
						arriveMarkerOption.position(point);
						arriveMarkerOption.title("도착지");
						arriveMarkerOption.draggable(false);
						arriveMarker = mGoogleMap.addMarker(arriveMarkerOption);
						arriveMarker.showInfoWindow();
						arriveLong = arriveMarkerOption.position(point).getPosition().longitude;
						arriveLat = arriveMarkerOption.position(point).getPosition().latitude;

					}
				}
			}
		});

	}

	@Override
	public void onMapClick(LatLng point) {

		// 현재 위도와 경도에서 화면 포인트를 알려준다
		Point screenPt = mGoogleMap.getProjection().toScreenLocation(point);

		// 현재 화면에 찍힌 포인트로 부터 위도와 경도를 알려준다.
		LatLng latLng = mGoogleMap.getProjection().fromScreenLocation(screenPt);

		// Log.DEBUG(this, "좌표: 위도(" + point.latitude + "), 경도(" +
		// point.longitude + ")", Toast.LENGTH_LONG);
		// Log.DEBUG(this, "화면좌표: X(" + screenPt.x + "), Y(" + screenPt.y + ")",
		// Toast.LENGTH_LONG);

		Log.d("맵좌표", "좌표: 위도(" + String.valueOf(point.latitude) + "), 경도(" + String.valueOf(point.longitude) + ")");
		Log.d("화면좌표", "화면좌표: X(" + String.valueOf(screenPt.x) + "), Y(" + String.valueOf(screenPt.y) + ")");
	}

}
