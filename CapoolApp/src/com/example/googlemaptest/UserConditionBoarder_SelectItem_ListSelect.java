package com.example.googlemaptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserConditionBoarder_SelectItem_ListSelect
		extends ListViewAdapter<UserConditionBoarder_SelectItem_ListData> {

	UserConditionBoarder_SelectItem_ListSelect(Context mContext) {
		super(mContext);
	}

	public void addItem( String mGrade, String mReview) {
		UserConditionBoarder_SelectItem_ListData addInfo = null;
		addInfo = new UserConditionBoarder_SelectItem_ListData();
		addInfo.mGrade = mGrade;
		addInfo.mReview = mReview;

		mListData.add(addInfo);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Boarding_Search_Select_ViewHolder holder;
		if (convertView == null) {
			holder = new Boarding_Search_Select_ViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.userconditionboarder_selectitem, null);

			holder.mGrade = (TextView) convertView.findViewById(R.id.UserConditionBoarder_SelectItem_Grade);
			holder.mReview = (TextView) convertView.findViewById(R.id.UserConditionBoarder_SelectItem_Review);

			convertView.setTag(holder);
		} else {
			holder = (Boarding_Search_Select_ViewHolder) convertView.getTag();
		}

		UserConditionBoarder_SelectItem_ListData mData = mListData.get(position);

	
		holder.mGrade.setText(mData.mGrade);
		holder.mReview.setText(mData.mReview);

		return convertView;
	}
}
