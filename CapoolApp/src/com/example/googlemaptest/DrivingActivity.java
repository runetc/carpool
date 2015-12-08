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
	int plag; // 1�̸� ��߹�ưŬ��, 2�� ������ưŬ��
	int startPlag = 0; // 0�̸� ���� 1�̸� ����.
	int arrivePlag = 0; // start�� ��������.
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
					// �̸��ϰ� ������ȣ�� ����.
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
						Toast.makeText(getApplicationContext(), "��Ī�� ��û�Ǿ����ϴ�. ��Ī����� ������ > ��û��Ȳ���� Ȯ�����ּ���. ",
								Toast.LENGTH_SHORT).show();
						finish();
					} else {
						Toast.makeText(getApplicationContext(), "��Ī�� �����Ͽ����ϴ�. �ٽýõ����ּ���", Toast.LENGTH_SHORT).show();
					}
				} else
					Toast.makeText(getApplicationContext(), "��ĭ�� ������ ä���ּ���", Toast.LENGTH_SHORT).show();
			}
		});
	}

	/*
	 * Map Ŭ���� ��ġ �̺�Ʈ
	 * 
	 * @see
	 * com.google.android.gms.maps.GoogleMap.OnMapClickListener#onMapClick(com
	 * .google.android.gms.maps.model.LatLng)
	 */
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

	/**
	 * �ʱ�ȭ
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

		// �װ����� ����
		mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		// ��ġ�̺�Ʈ ����
		mGoogleMap.setOnMapClickListener(this);

		// �� ��ġ�̵�.
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

		/** �簢�� �׸��� */
		// this.setUpMap(lat, lng);

		/** �ʿ� �ٿ� ���� BitmapDescriptor ���� */
		/*
		 * BitmapDescriptor descriptor =
		 * BitmapDescriptorFactory.fromResource(R.id.map);
		 * 
		 * // �ٿ� �ֱ� �ɼ� ���� LatLng sw = new LatLng(lat, lng); LatLng nw = new
		 * LatLng(lat, lng); LatLngBounds mapBounds = new LatLngBounds(sw, nw);
		 * 
		 * GroundOverlayOptions options = new GroundOverlayOptions();
		 * options.image(descriptor); options.positionFromBounds(mapBounds);
		 * options.transparency(0.5f); //options.anchor(0, 1);
		 * //options.position(position, 80);
		 * 
		 * // �ʿ� ���İ� ���� GroundOverlay overlay =
		 * mGoogleMap.addGroundOverlay(options); overlay.setTransparency(0.5F);
		 */

		/** ��Ŀ �����ϱ� */
		/*
		 * // ù��° ��Ŀ ����. MarkerOptions optFirst = new MarkerOptions();
		 * optFirst.position(position);// ���� �� �浵 optFirst.title(title);// ����
		 * �̸����� optFirst.snippet("Snippet");
		 * optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.
		 * ic_launcher)); mGoogleMap.addMarker(optFirst).showInfoWindow();
		 * 
		 * // �ι�° ��Ŀ ����. MarkerOptions optSecond = new MarkerOptions();
		 * optSecond.position(new LatLng(36.144269, 128.392625));// ���� �� �浵
		 * optSecond.title(title); // ���� �̸����� optSecond.snippet("Snippet2");
		 * mGoogleMap.addMarker(optSecond).showInfoWindow();
		 */

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

	private void setUpMap(double lat, double lng) {
		// ����
		PolygonOptions options = new PolygonOptions();
		// �׸��� ��ǥ�� ����
		options.addAll(createRectangle(new LatLng(lat, lng), 0.005, 0.005));
		// ����
		options.addHole(createRectangle(new LatLng(lat, lng), 0.001, 0.001));
		// ä���
		options.fillColor(0x44ff0000);
		// ��
		options.strokeColor(Color.RED);
		// ����
		options.strokeWidth(5);
		// �׸���
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