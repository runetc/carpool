package com.example.googlemaptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserEstimate_ListSelect extends ListViewAdapter<UserEstimate_ListData> {

	UserEstimate_ListSelect(Context mContext) {
		super(mContext);
	}

	public void addItem(String mCount, String mDate, String mId) {
		UserEstimate_ListData addInfo = null;
		addInfo = new UserEstimate_ListData();
		addInfo.mCount = mCount;
		addInfo.mDate = mDate;
		addInfo.mId = mId;
	

		mListData.add(addInfo);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		UserEstimate_ViewHolder holder;
		if (convertView == null) {
			holder = new UserEstimate_ViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.userestimateviewitem, null);

			holder.mCount = (TextView) convertView.findViewById(R.id.userestimateviewitem_count);
			holder.mDate = (TextView) convertView.findViewById(R.id.userestimateviewitem_date);
			holder.mId = (TextView) convertView.findViewById(R.id.userestimateviewitem_id);
			
			convertView.setTag(holder);
		} else {
			holder = (UserEstimate_ViewHolder) convertView.getTag();
		}

		UserEstimate_ListData mData = mListData.get(position);
		holder.mCount.setText(mData.mCount);
		holder.mDate.setText(mData.mDate);
		holder.mId.setText(mData.mId);
		
		return convertView;
	}
}
