package com.example.googlemaptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserConditionDriver_ListSelect extends ListViewAdapter<UserConditionDriver_ListData> {

	UserConditionDriver_ListSelect(Context mContext) {
		super(mContext);
	}

	public void addItem(String mId, String mGrade) {
		UserConditionDriver_ListData addInfo = null;
		addInfo = new UserConditionDriver_ListData();
		addInfo.mId = mId;
		addInfo.mGrade = mGrade;
		mListData.add(addInfo);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		UserConditionDriver_ViewHolder holder;
		if (convertView == null) {
			holder = new UserConditionDriver_ViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.userconditiondriveritem, null);

			holder.mId = (TextView) convertView.findViewById(R.id.UserConditionDriverItem_Id);
			holder.mGrade = (TextView) convertView.findViewById(R.id.UserConditionDriverItem_Grade);

			convertView.setTag(holder);
		} else {
			holder = (UserConditionDriver_ViewHolder) convertView.getTag();
		}

		UserConditionDriver_ListData mData = mListData.get(position);
		holder.mId.setText(mData.mId);
		holder.mGrade.setText(mData.mGrade);

		return convertView;
	}
}
