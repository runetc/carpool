package com.example.googlemaptest;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class UserEstimate_Viewscore_ViewAdapter<T> extends BaseAdapter {
	protected Context mContext = null;
	protected ArrayList<T> mListData = new ArrayList<T>();

	public UserEstimate_Viewscore_ViewAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return mListData.size();
	}

	@Override
	public Object getItem(int position) {
		return mListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
}