package com.wedrips.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ChatFaceAdapter extends BaseAdapter {
	
	private int[] faces;
	private int keyboardheight;
	private Context context;

	public ChatFaceAdapter(Context context, int[] faces,int keyboardheight) {
		this.context=context;
		this.faces=faces;
		this.keyboardheight=keyboardheight;
	}

	@Override
	public int getCount() {
		return faces.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView view = new ImageView(context);
		view.setLayoutParams(new AbsListView.LayoutParams(keyboardheight/4,keyboardheight/4));
		view.setPadding(keyboardheight/30, keyboardheight/30, keyboardheight/30, keyboardheight/30);
		view.setImageResource(faces[position]);
		view.setScaleType(ImageView.ScaleType.FIT_CENTER);
		return view;
	}

}
