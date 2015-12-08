package com.example.googlemaptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserEstimate_Viewscore_ListSelect
		extends UserEstimate_Viewscore_ViewAdapter<UserEstimate_Viewscore_ListData> {
	// 여기에 들어갈꺼는
	UserEstimate_Viewscore_ListSelect(Context mContext) {
		super(mContext);
	}

	public void addItem(String mNum, String mScore, String mContents) {
		UserEstimate_Viewscore_ListData addInfo = null;
		addInfo = new UserEstimate_Viewscore_ListData();
		addInfo.mNum = mNum;
		addInfo.mScore = mScore;
		addInfo.mContents = mContents;

		mListData.add(addInfo);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		UserEstimate_Viewscore_ViewHolder holder;
		if (convertView == null) {
			holder = new UserEstimate_Viewscore_ViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.userestimate_viewscoreitem, null);

			holder.mNum = (TextView) convertView.findViewById(R.id.userEstimate_ViewScoreItem_Num);
			holder.mScore = (TextView) convertView.findViewById(R.id.userEstimate_ViewScoreItem_Score);
			holder.mContents = (TextView) convertView.findViewById(R.id.userEstimate_ViewScoreItem_Contents);

			convertView.setTag(holder);
		} else {
			holder = (UserEstimate_Viewscore_ViewHolder) convertView.getTag();
		}

		UserEstimate_Viewscore_ListData mData = mListData.get(position);

		holder.mNum.setText(mData.mNum);
		holder.mScore.setText(mData.mScore);
		holder.mContents.setText(mData.mContents);

		return convertView;
	}
}
