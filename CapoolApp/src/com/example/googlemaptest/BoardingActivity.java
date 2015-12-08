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
import android.widget.TextView;
import android.widget.Toast;

public class BoardingActivity extends FragmentActivity implements OnMapClickListener {
	Datetime dt;
	Button matchingBtn;
	GoogleMap mGoogleMap;
	TextView timeTV;
	Marker startMarker;
	Marker arriveMarker;
	MarkerOptions startMarkerOption;
	MarkerOptions arriveMarkerOption;

	int plag; // 1�̸� ��߹�ưŬ��, 2�� ������ưŬ��
	int startPlag = 0; // 0�̸� ���� 1�̸� ����.
	int arrivePlag = 0; // start�� ��������.
	double startLong = 0;
	double startLat = 0;
	double arriveLong = 0;
	double arriveLat = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boarding);
		Intent intent = getIntent();
		final String login_id = intent.getExtras().getString("login_id");

		init();

		timeTV = (TextView) findViewById(R.id.Boarding_Time);
		timeTV.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(BoardingActivity.this, DatetimeActivity.class);
				startActivityForResult(i, 0);
			}
		});
		Button StartBtn = (Button) findViewById(R.id.boarding_StartBtn);
		StartBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				plag = 1;

			}
		});
		Button ArriveBtn = (Button) findViewById(R.id.boarding_ArriveBtn);
		ArriveBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				plag = 2;

			}
		});

		final Button matchingBtn = (Button) findViewById(R.id.boarding_matching);
		matchingBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (startLong != 0 && arriveLong != 0) {
					BoardingThread BSThread = new BoardingThread();

					String[] s = { timeTV.getText().toString(), Double.toString(startLong), Double.toString(startLat),
							Double.toString(arriveLong), Double.toString(arriveLat) };
					BSThread.setSnumber(login_id);
					BSThread.inputProcessNum(9);
					BSThread.inputText(s);
					BSThread.start();

					String[] SA = BSThread.getInputMsg();
					while (!BSThread.getWorkComplete())
						;
					if (BSThread.getCheckJoinComplete()) {

						Toast.makeText(getApplicationContext(), "��ȸ �Ϸ�!", Toast.LENGTH_SHORT).show();
						Intent i = new Intent(BoardingActivity.this, Boarding_SearchActivity.class);
						i.putExtra("SA", SA);
						i.putExtra("login_id", login_id);
						startActivity(i);
					} else {
						Toast.makeText(getApplicationContext(), "��ȸ�� �����Ͽ����ϴ�. �ٽ� �õ� ���ּ���", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "��, �������� �������� �ʾҽ��ϴ�. ", Toast.LENGTH_SHORT).show();

				}

			}
		});
		/*
		 * matchingBtn = (Button) findViewById(R.id.taxi_matching);
		 * matchingBtn.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { String msg;
		 * 
		 * if (matchingBtn.getText() == "��Ī") { msg = "���"; } else { msg = "��Ī";
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
		GooglePlayServicesUtil.isGooglePlayServicesAvailable(BoardingActivity.this);

		mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

		// �װ����� ����
		mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		// ��ġ�̺�Ʈ ����
		mGoogleMap.setOnMapClickListener(this);

		// �� ��ġ�̵�.
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

		// ��Ŀ Ŭ�� ������
		mGoogleMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			public boolean onMarkerClick(Marker marker) {
				String text = "[��Ŀ Ŭ�� �̺�Ʈ] latitude =" + marker.getPosition().latitude + ", longitude ="
						+ marker.getPosition().longitude;
				Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
				return false;
			}
		});

		// Ŭ���̺�Ʈ
		mGoogleMap.setOnMapClickListener(new OnMapClickListener() {

			public void onMapClick(LatLng point) {
				if (plag == 1) {
					if (startPlag == 0) {

						startMarkerOption = new MarkerOptions();
						startMarkerOption.position(point);
						startMarkerOption.title("�����");
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
						startMarkerOption.title("�����");
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
						arriveMarkerOption.title("������");
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
						arriveMarkerOption.title("������");
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

		// ���� ������ �浵���� ȭ�� ����Ʈ�� �˷��ش�
		Point screenPt = mGoogleMap.getProjection().toScreenLocation(point);

		// ���� ȭ�鿡 ���� ����Ʈ�� ���� ������ �浵�� �˷��ش�.
		LatLng latLng = mGoogleMap.getProjection().fromScreenLocation(screenPt);

		// Log.DEBUG(this, "��ǥ: ����(" + point.latitude + "), �浵(" +
		// point.longitude + ")", Toast.LENGTH_LONG);
		// Log.DEBUG(this, "ȭ����ǥ: X(" + screenPt.x + "), Y(" + screenPt.y + ")",
		// Toast.LENGTH_LONG);

		Log.d("����ǥ", "��ǥ: ����(" + String.valueOf(point.latitude) + "), �浵(" + String.valueOf(point.longitude) + ")");
		Log.d("ȭ����ǥ", "ȭ����ǥ: X(" + String.valueOf(screenPt.x) + "), Y(" + String.valueOf(screenPt.y) + ")");
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
