package com.example.googlemaptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BoardReading_ListSelect extends BoardReading_ListViewAdapter<BoardReading_ListData> {
	// 여기에 들어갈꺼는
	BoardReading_ListSelect(Context mContext) {
		super(mContext);
	}

	public void addItem(String mWriter, String mDate, String mContents) {
		BoardReading_ListData addInfo = null;
		addInfo = new BoardReading_ListData();
		addInfo.mWriter = mWriter;
		addInfo.mDate = mDate;
		addInfo.mContents = mContents;

		mListData.add(addInfo);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		BoardReading_ViewHolder holder;
		if (convertView == null) {
			holder = new BoardReading_ViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.boardreading_replyitem, null);

			holder.mWriter = (TextView) convertView.findViewById(R.id.board_Reply_Writer);
			holder.mDate = (TextView) convertView.findViewById(R.id.board_Reply_Date);
			holder.mContents = (TextView) convertView.findViewById(R.id.board_Reply_Contents);

			convertView.setTag(holder);
		} else {
			holder = (BoardReading_ViewHolder) convertView.getTag();
		}

		BoardReading_ListData mData = mListData.get(position);

		holder.mWriter.setText(mData.mWriter);
		holder.mDate.setText(mData.mDate);
		holder.mContents.setText(mData.mContents);

		return convertView;
	}
}
