package com.example.googlemaptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Boarding_Search_ListSelect extends Boarding_Search_ListViewAdapter<Boarding_Search_ListData> {
	Boarding_Search_ListSelect(Context mContext) {
		super(mContext);
	}

	public void addItem(String mId, String mStart, String mArrive, String mTime, String mPay) {
		Boarding_Search_ListData addInfo = null;
		addInfo = new Boarding_Search_ListData();
		addInfo.mId = mId;
		addInfo.mStart = mStart;
		addInfo.mArrive = mArrive;
		addInfo.mTime = mTime;
		addInfo.mPay = mPay;

		mListData.add(addInfo);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Boarding_Search_ViewHolder holder;
		if (convertView == null) {
			holder = new Boarding_Search_ViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.boarding_searchitem, null);

			holder.mId = (TextView) convertView.findViewById(R.id.Boarding_SearchItem_Id);
			holder.mStart = (TextView) convertView.findViewById(R.id.Boarding_SearchItem_Start);
			holder.mArrive = (TextView) convertView.findViewById(R.id.Boarding_SearchItem_Arrive);
			holder.mTime = (TextView) convertView.findViewById(R.id.Boarding_SearchItem_Time);
			holder.mPay = (TextView) convertView.findViewById(R.id.Boarding_SearchItem_Pay);

			convertView.setTag(holder);
		} else {
			holder = (Boarding_Search_ViewHolder) convertView.getTag();
		}

		Boarding_Search_ListData mData = mListData.get(position);
		holder.mId.setText(mData.mId);
		holder.mStart.setText(mData.mStart);
		holder.mArrive.setText(mData.mArrive);
		holder.mTime.setText(mData.mTime);
		holder.mPay.setText(mData.mPay);

		return convertView;
	}
}
