package com.example.googlemaptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserConditionBoarder_ListSelect extends ListViewAdapter<UserConditionBoarder_ListData> {

	UserConditionBoarder_ListSelect(Context mContext) {
		super(mContext);
	}

	public void addItem(String mCount, String mStart, String mArrive, String mTime, String mLimit) {
		UserConditionBoarder_ListData addInfo = null;
		addInfo = new UserConditionBoarder_ListData();
		addInfo.mCount = mCount;
		addInfo.mStart = mStart;
		addInfo.mArrive = mArrive;
		addInfo.mTime = mTime;
		addInfo.mLimit = mLimit;

		mListData.add(addInfo);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		UserConditionBoarder_ViewHolder holder;
		if (convertView == null) {
			holder = new UserConditionBoarder_ViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.userconditionboarderitem, null);

			holder.mCount = (TextView) convertView.findViewById(R.id.UserConditionBoarderItem_Count);
			holder.mStart = (TextView) convertView.findViewById(R.id.UserConditionBoarderItem_Start);
			holder.mArrive = (TextView) convertView.findViewById(R.id.UserConditionBoarderItem_Arrive);
			holder.mTime = (TextView) convertView.findViewById(R.id.UserConditionBoarderItem_Time);
			holder.mLimit = (TextView) convertView.findViewById(R.id.UserConditionBoarderItem_Limit);

			convertView.setTag(holder);
		} else {
			holder = (UserConditionBoarder_ViewHolder) convertView.getTag();
		}

		UserConditionBoarder_ListData mData = mListData.get(position);
		holder.mCount.setText(mData.mCount);
		holder.mStart.setText(mData.mStart);
		holder.mArrive.setText(mData.mArrive);
		holder.mTime.setText(mData.mTime);
		holder.mLimit.setText(mData.mLimit);

		return convertView;
	}
}
