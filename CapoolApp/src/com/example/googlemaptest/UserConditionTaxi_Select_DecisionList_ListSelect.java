package com.example.googlemaptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserConditionTaxi_Select_DecisionList_ListSelect
		extends ListViewAdapter<UserConditionTaxi_Select_DecisionList_ListData> {

	UserConditionTaxi_Select_DecisionList_ListSelect(Context mContext) {
		super(mContext);
	}

	public void addItem(String mCount, String mTelNum) {
		UserConditionTaxi_Select_DecisionList_ListData addInfo = null;
		addInfo = new UserConditionTaxi_Select_DecisionList_ListData();
		addInfo.mCount = mCount;
		addInfo.mTelNum = mTelNum;

		mListData.add(addInfo);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		UserConditionTaxi_Select_DecisionList_ViewHolder holder;
		if (convertView == null) {
			holder = new UserConditionTaxi_Select_DecisionList_ViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.userconditiontaxi_select_decisionlistitem, null);

			holder.mCount = (TextView) convertView.findViewById(R.id.userconditiontaxi_select_decisionlistitem_Count);
			holder.mTelNum = (TextView) convertView.findViewById(R.id.userconditiontaxi_select_decisionlistitem_TelNum);

			convertView.setTag(holder);
		} else {
			holder = (UserConditionTaxi_Select_DecisionList_ViewHolder) convertView.getTag();
		}

		UserConditionTaxi_Select_DecisionList_ListData mData = mListData.get(position);
		holder.mCount.setText(mData.mCount);
		holder.mTelNum.setText(mData.mTelNum);

		return convertView;
	}
}
