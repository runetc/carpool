package com.example.googlemaptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserConditionTaxi_ListSelect extends ListViewAdapter<UserConditionTaxi_ListData> {

	UserConditionTaxi_ListSelect(Context mContext) {
		super(mContext);
	}

	public void addItem(String mCount, String mStart, String mArrive, String mTime, String mLimit) {
		UserConditionTaxi_ListData addInfo = null;
		addInfo = new UserConditionTaxi_ListData();
		addInfo.mCount = mCount;
		addInfo.mStart = mStart;
		addInfo.mArrive = mArrive;
		addInfo.mTime = mTime;
		addInfo.mLimit = mLimit;

		mListData.add(addInfo);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		UserConditionTaxi_ViewHolder holder;
		if (convertView == null) {
			holder = new UserConditionTaxi_ViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.userconditiontaxiitem, null);

			holder.mCount = (TextView) convertView.findViewById(R.id.UserConditionTaxiItem_Count);
			holder.mStart = (TextView) convertView.findViewById(R.id.UserConditionTaxiItem_Start);
			holder.mArrive = (TextView) convertView.findViewById(R.id.UserConditionTaxiItem_Arrive);
			holder.mTime = (TextView) convertView.findViewById(R.id.UserConditionTaxiItem_Time);
			holder.mLimit = (TextView) convertView.findViewById(R.id.UserConditionTaxiItem_Limit);

			convertView.setTag(holder);
		} else {
			holder = (UserConditionTaxi_ViewHolder) convertView.getTag();
		}

		UserConditionTaxi_ListData mData = mListData.get(position);
		holder.mCount.setText(mData.mCount);
		holder.mStart.setText(mData.mStart);
		holder.mArrive.setText(mData.mArrive);
		holder.mTime.setText(mData.mTime);
		holder.mLimit.setText(mData.mLimit);

		return convertView;
	}
}
