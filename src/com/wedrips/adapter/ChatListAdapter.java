package com.wedrips.adapter;

import com.wedrips.R;
import com.wedrips.account.AccountActivity;
import com.wedrips.chat.ChatActivity;
import com.wedrips.chat.ChatListEntity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatListAdapter extends BaseAdapter {
	public Context context;
	public ChatListEntity[] datas;

	public ChatListAdapter(Context context, ChatListEntity[] datas) {
		this.context = context;
		this.datas = datas;
	}
	public String[] msgDataA=new String[]{"丰富线下生活！","因梦想而不甘平庸！"};
	public String[] msgDataB=new String[]{"超乎想象的成长空间","充满挑战性的工作","与千里马共事的机会","无与伦比的成就感"};

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.chat_list_cell, null);
		}
		ChatListEntity data = getItem(position);
		ImageView img = (ImageView) convertView.findViewById(R.id.head);
		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView time = (TextView) convertView.findViewById(R.id.time);

		img.setImageResource(data.imgid);
		name.setText(data.name);
		time.setText(data.time);

		img.setOnClickListener(headlisten(position));
		name.setOnClickListener(textlisten(position));
		time.setOnClickListener(textlisten(position));

		return convertView;
	}

	@Override
	public int getCount() {
		return datas.length;
	}

	@Override
	public ChatListEntity getItem(int position) {
		return datas[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/*-----------------------监听器---------------------------*/
	
	public OnClickListener headlisten(final int position) {
		OnClickListener listen = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, AccountActivity.class);
				intent.putExtra("name", datas[position].name.toString());
				intent.putExtra("headimg", datas[position].imgid);
				if (datas[position].name.equals("Wchuan")) {
					
					intent.putExtra("accountid", 0);
				} else {
					intent.putExtra("accountid", 1);
				}
				context.startActivity(intent);
			}
		};
		return listen;
	}

	public OnClickListener textlisten(final int position) {
		OnClickListener listen = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ChatActivity.class);
				intent.putExtra("name", datas[position].name.toString());
				if (datas[position].name.equals("Wchuan")) {
					intent.putExtra("msgdata", msgDataA);
				} else {
					intent.putExtra("msgdata", msgDataB);
				}
				context.startActivity(intent);
			}
		};
		return listen;
	}
	public class ViewHolder{
		ImageView imgHead;
		TextView name;
		TextView time;
	}
}
