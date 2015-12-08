package com.example.googlemaptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BoardMain_ListSelect extends BoardMain_ListViewAdapter<BoardMain_ListData> {
	// 여기에 들어갈꺼는
	BoardMain_ListSelect(Context mContext) {
		super(mContext);
	}

	public void addItem(String mNum, String mTitle, String mWriter, String mDate) {
		BoardMain_ListData addInfo = null;
		addInfo = new BoardMain_ListData();
		addInfo.mNum = mNum;
		addInfo.mTitle = mTitle;
		addInfo.mWriter = mWriter;
		addInfo.mDate = mDate;

		mListData.add(addInfo);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		BoardMain_ViewHolder holder;
		if (convertView == null) {
			holder = new BoardMain_ViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.boardmainitem, null);

			holder.mNum = (TextView) convertView.findViewById(R.id.boardFirstItem_Num);
			holder.mTitle = (TextView) convertView.findViewById(R.id.boardFirstItem_Title);
			holder.mWriter = (TextView) convertView.findViewById(R.id.boardFirstItem_Writer);
			holder.mDate = (TextView) convertView.findViewById(R.id.boardFirstItem_Date);

			convertView.setTag(holder);
		} else {
			holder = (BoardMain_ViewHolder) convertView.getTag();
		}

		BoardMain_ListData mData = mListData.get(position);

		holder.mNum.setText(mData.mNum);
		holder.mTitle.setText(mData.mTitle);
		holder.mWriter.setText(mData.mWriter);
		holder.mDate.setText(mData.mDate);

		return convertView;
	}
}
