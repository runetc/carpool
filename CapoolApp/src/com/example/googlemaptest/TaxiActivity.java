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
		matchingBtn.setText("��Ī");
		matchingBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				String msg;

				// ������ ��ǥ�� �����Ǿ��� Ȯ���Ѵ�.
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
						Toast.makeText(getApplicationContext(), "��Ī�� ��û�Ǿ����ϴ�. ��Ī����� ������ > ��û��Ȳ���� Ȯ�����ּ���. ",
								Toast.LENGTH_SHORT).show();
						finish();
					} else {
						Toast.makeText(getApplicationContext(), "��Ī�� �����Ͽ����ϴ�. �ٽýõ����ּ���", Toast.LENGTH_SHORT).show();
						
					}
				} else
					Toast.makeText(getApplicationContext(), "��, �������� ����� �������� �ʾҽ��ϴ�.", Toast.LENGTH_SHORT).show();
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
		GooglePlayServicesUtil.isGooglePlayServicesAvailable(TaxiActivity.this);

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

}
