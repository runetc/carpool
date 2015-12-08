package com.example.googlemaptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Boarding_Search_Select_ListSelect extends ListViewAdapter<Boarding_Search_Select_ListData> {

	Boarding_Search_Select_ListSelect(Context mContext) {
		super(mContext);
	}

	public void addItem(String mId, String mGrade, String mReview) {
		Boarding_Search_Select_ListData addInfo = null;
		addInfo = new Boarding_Search_Select_ListData();
		addInfo.mId = mId;
		addInfo.mGrade = mGrade;
		addInfo.mReview = mReview;

		mListData.add(addInfo);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Boarding_Search_Select_ViewHolder holder;
		if (convertView == null) {
			holder = new Boarding_Search_Select_ViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.boarding_search_selectitem, null);

			holder.mId = (TextView) convertView.findViewById(R.id.Boarding_Search_SelectItem_Id);
			holder.mGrade = (TextView) convertView.findViewById(R.id.Boarding_Search_SelectItem_Grade);
			holder.mReview = (TextView) convertView.findViewById(R.id.Boarding_Search_SelectItem_Review);

			convertView.setTag(holder);
		} else {
			holder = (Boarding_Search_Select_ViewHolder) convertView.getTag();
		}

		Boarding_Search_Select_ListData mData = mListData.get(position);

		holder.mId.setText(mData.mId);
		holder.mGrade.setText(mData.mGrade);
		holder.mReview.setText(mData.mReview);

		return convertView;
	}
}
