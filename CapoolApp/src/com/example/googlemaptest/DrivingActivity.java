package com.example.googlemaptest;

import java.util.Arrays;
import java.util.List;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DrivingActivity extends FragmentActivity implements OnMapClickListener {
	Datetime dt;
	TextView timeTV;
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
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.driving);
		Intent intent = getIntent();
		final String login_id = intent.getExtras().getString("login_id");

		init();

		timeTV = (TextView) findViewById(R.id.Driving_Time);
		timeTV.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(DrivingActivity.this, DatetimeActivity.class);
				startActivityForResult(i, 0);
			}
		});

		Button StartBtn = (Button) findViewById(R.id.Driving_StartBtn);
		StartBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				plag = 1;

			}
		});
		Button ArriveBtn = (Button) findViewById(R.id.Driving_ArriveBtn);
		ArriveBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				plag = 2;

			}
		});
		Button matchingBtn = (Button) findViewById(R.id.Driving_Matching);
		matchingBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText Cost = (EditText) findViewById(R.id.Driving_Cost);
				EditText Person = (EditText) findViewById(R.id.Driving_Person);
				if (startLong != 0 && arriveLong != 0 && !Cost.getText().toString().equals("")
						&& !Person.getText().toString().equals("")
						&& !timeTV.getText().toString().equals("0000-00-00 00:00")) {
					NormalThread DrivingThread = new NormalThread();
					// 이메일과 인증번호만 보냄.
					String[] s = { timeTV.getText().toString(), Double.toString(startLong), Double.toString(startLat),
							Double.toString(arriveLong), Double.toString(arriveLat), Cost.getText().toString(),
							Person.getText().toString() };
					DrivingThread.setSnumber(login_id);
					DrivingThread.inputProcessNum(8);
					DrivingThread.inputText(s);
					DrivingThread.start();

					while (!DrivingThread.getWorkComplete())
						;

					if (DrivingThread.getCheckJoinComplete()) {
						Toast.makeText(getApplicationContext(), "매칭이 신청되었습니다. 매칭결과는 내상태 > 신청현황에서 확인해주세요. ",
								Toast.LENGTH_SHORT).show();
						finish();
					} else {
						Toast.makeText(getApplicationContext(), "매칭이 실패하였습니다. 다시시도해주세요", Toast.LENGTH_SHORT).show();
					}
				} else
					Toast.makeText(getApplicationContext(), "빈칸이 없도록 채워주세요", Toast.LENGTH_SHORT).show();
			}
		});
	}

	/*
	 * Map 클릭시 터치 이벤트
	 * 
	 * @see
	 * com.google.android.gms.maps.GoogleMap.OnMapClickListener#onMapClick(com
	 * .google.android.gms.maps.model.LatLng)
	 */
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

	/**
	 * 초기화
	 * 
	 * @author gon 2014. 2. 16.
	 */
	private void init() {

		Intent getI = getIntent();

		String title = getI.getStringExtra("marker test 1");
		String coordinates[] = { "36.144269", "128.392625" };
		double lat = Double.parseDouble(coordinates[0]);
		double lng = Double.parseDouble(coordinates[1]);

		LatLng position = new LatLng(lat, lng);
		GooglePlayServicesUtil.isGooglePlayServicesAvailable(DrivingActivity.this);

		mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

		// 항공사진 변경
		mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		// 터치이벤트 설정
		mGoogleMap.setOnMapClickListener(this);

		// 맵 위치이동.
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

		/** 사각형 그리기 */
		// this.setUpMap(lat, lng);

		/** 맵에 붙여 넣을 BitmapDescriptor 생성 */
		/*
		 * BitmapDescriptor descriptor =
		 * BitmapDescriptorFactory.fromResource(R.id.map);
		 * 
		 * // 붙여 넣기 옵션 설정 LatLng sw = new LatLng(lat, lng); LatLng nw = new
		 * LatLng(lat, lng); LatLngBounds mapBounds = new LatLngBounds(sw, nw);
		 * 
		 * GroundOverlayOptions options = new GroundOverlayOptions();
		 * options.image(descriptor); options.positionFromBounds(mapBounds);
		 * options.transparency(0.5f); //options.anchor(0, 1);
		 * //options.position(position, 80);
		 * 
		 * // 맵에 알파값 설정 GroundOverlay overlay =
		 * mGoogleMap.addGroundOverlay(options); overlay.setTransparency(0.5F);
		 */

		/** 마커 설정하기 */
		/*
		 * // 첫번째 마커 설정. MarkerOptions optFirst = new MarkerOptions();
		 * optFirst.position(position);// 위도 · 경도 optFirst.title(title);// 제목
		 * 미리보기 optFirst.snippet("Snippet");
		 * optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.
		 * ic_launcher)); mGoogleMap.addMarker(optFirst).showInfoWindow();
		 * 
		 * // 두번째 마커 설정. MarkerOptions optSecond = new MarkerOptions();
		 * optSecond.position(new LatLng(36.144269, 128.392625));// 위도 · 경도
		 * optSecond.title(title); // 제목 미리보기 optSecond.snippet("Snippet2");
		 * mGoogleMap.addMarker(optSecond).showInfoWindow();
		 */

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

	private void setUpMap(double lat, double lng) {
		// 설정
		PolygonOptions options = new PolygonOptions();
		// 그리기 좌표를 설정
		options.addAll(createRectangle(new LatLng(lat, lng), 0.005, 0.005));
		// 빼기
		options.addHole(createRectangle(new LatLng(lat, lng), 0.001, 0.001));
		// 채우기
		options.fillColor(0x44ff0000);
		// 선
		options.strokeColor(Color.RED);
		// 선폭
		options.strokeWidth(5);
		// 그리기
		mGoogleMap.addPolygon(options);
	}

	private List<LatLng> createRectangle(LatLng center, Double halfWidth, Double halfHeight) {
		return Arrays.asList(new LatLng(center.latitude - halfHeight, center.longitude - halfWidth),
				new LatLng(center.latitude - halfHeight, center.longitude + halfWidth),
				new LatLng(center.latitude + halfHeight, center.longitude + halfWidth),
				new LatLng(center.latitude + halfHeight, center.longitude - halfWidth),
				new LatLng(center.latitude - halfHeight, center.longitude - halfWidth));
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 0:
			dt = new Datetime(data.getIntExtra("year", -1), data.getIntExtra("month", -1), data.getIntExtra("day", -1),
					data.getIntExtra("hour", -1), data.getIntExtra("minute", -1));
			String msg = "";
			msg += String.valueOf(dt.getYear()) + "-";
			msg += String.valueOf(dt.getMonth()) + "-";
			msg += String.valueOf(dt.getDay()) + " ";
			msg += String.valueOf(dt.getHour()) + ":";
			msg += String.valueOf(dt.getMinute());
			timeTV.setText(msg);
		}
	}
}