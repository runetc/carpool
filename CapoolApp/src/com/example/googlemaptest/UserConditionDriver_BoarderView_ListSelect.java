package com.example.googlemaptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserConditionDriver_BoarderView_ListSelect
		extends ListViewAdapter<UserConditionDriver_BoarderView_ListData> {

	UserConditionDriver_BoarderView_ListSelect(Context mContext) {
		super(mContext);
	}

	public void addItem(String mId, String mPnum) {
		UserConditionDriver_BoarderView_ListData addInfo = null;
		addInfo = new UserConditionDriver_BoarderView_ListData();
		addInfo.mId = mId;
		addInfo.mPnum = mPnum;

		mListData.add(addInfo);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		UserConditionDriver_BoarderView_ViewHolder holder;
		if (convertView == null) {
			holder = new UserConditionDriver_BoarderView_ViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.userconditiondriver_boarderviewitem, null);

			holder.mId = (TextView) convertView.findViewById(R.id.UserConditionDriver_BoarderViewItem_Id);
			holder.mPnum = (TextView) convertView.findViewById(R.id.UserConditionDriver_BoarderViewItem_Pnumber);

			convertView.setTag(holder);
		} else {
			holder = (UserConditionDriver_BoarderView_ViewHolder) convertView.getTag();
		}

		UserConditionDriver_BoarderView_ListData mData = mListData.get(position);
		holder.mId.setText(mData.mId);
		holder.mPnum.setText(mData.mPnum);

		return convertView;
	}
}
