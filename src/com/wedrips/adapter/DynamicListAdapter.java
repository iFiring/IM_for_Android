package com.wedrips.adapter;

import java.io.Serializable;
import java.util.List;

import com.wedrips.R;
import com.wedrips.account.AccountActivity;
import com.wedrips.dynamic.DynamicDetailView;
import com.wedrips.dynamic.DynamicValueEntity;
import com.wedrips.dynamic.DynamicViewHolder;
import com.wedrips.view.PhotoAnimationView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DynamicListAdapter extends BaseAdapter {
	public Context context;
	public List<DynamicValueEntity> viewEntity;
	public int windowswidth;

	public DynamicListAdapter(Context context, List<DynamicValueEntity> viewEntity, int windowswidth) {
		this.context = context;
		this.viewEntity = viewEntity;
		this.windowswidth = windowswidth;
	}

	@Override
	public int getCount() {
		return viewEntity.size();
	}

	@Override
	public DynamicValueEntity getItem(int position) {
		return viewEntity.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public int getItemViewType(int position) {
		DynamicValueEntity entity = viewEntity.get(position);
		switch (entity.dynamicType) {
		case 0:
			return 0;
		case 1:
			return 1;
		default:
			return 0;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DynamicValueEntity entity = viewEntity.get(position);
		int dynamicType = entity.dynamicType;

		DynamicViewHolder viewholder = null;
		if (convertView == null) {
			viewholder = new DynamicViewHolder();
			switch (dynamicType) {
			case 0:
				convertView = LayoutInflater.from(context).inflate(R.layout.dynamic_layout, null);
				viewholder.userHead = (ImageView) convertView.findViewById(R.id.head);
				viewholder.userName = (TextView) convertView.findViewById(R.id.name);
				viewholder.dynamicTime = (TextView) convertView.findViewById(R.id.time);
				viewholder.dynamicText = (TextView) convertView.findViewById(R.id.text);
				viewholder.dynamicPhoto = (PhotoAnimationView) convertView.findViewById(R.id.photo);
				viewholder.dynamicHeart = (ImageView) convertView.findViewById(R.id.heart);
				viewholder.dynamicComments = (ImageView) convertView.findViewById(R.id.comments);
				viewholder.dynamicShare = (ImageView) convertView.findViewById(R.id.share);
				
				convertView.setTag(viewholder);
				break;
			case 1:
				convertView = LayoutInflater.from(context).inflate(R.layout.activity_layout, null);
				viewholder.userHead = (ImageView) convertView.findViewById(R.id.head);
				viewholder.userName = (TextView) convertView.findViewById(R.id.name);
				viewholder.dynamicTime = (TextView) convertView.findViewById(R.id.time);
				viewholder.dynamicText = (TextView) convertView.findViewById(R.id.text);
				viewholder.activityTitle=(TextView) convertView.findViewById(R.id.title);
				viewholder.activityPhoto = (ImageView) convertView.findViewById(R.id.photo);
				viewholder.activityTime=(TextView) convertView.findViewById(R.id.activitytime);
				viewholder.activityAddress=(TextView) convertView.findViewById(R.id.activityaddress);
				viewholder.dynamicHeart = (ImageView) convertView.findViewById(R.id.heart);
				viewholder.dynamicComments = (ImageView) convertView.findViewById(R.id.comments);
				viewholder.dynamicShare = (ImageView) convertView.findViewById(R.id.share);
				convertView.setTag(viewholder);
				break;
			default:
				
			}

		} else {
			viewholder = (DynamicViewHolder) convertView.getTag();
		}
		viewholder.userHead.setImageResource(viewEntity.get(position).userHead);
		viewholder.userName.setText(viewEntity.get(position).userName);
		viewholder.dynamicTime.setText(viewEntity.get(position).Time);
		viewholder.dynamicText.setText(viewEntity.get(position).Text);

		viewholder.userHead.setOnClickListener(imageListener("head", viewholder.userHead, position));
		viewholder.userName.setOnClickListener(imageListener("head", viewholder.userHead, position));
		viewholder.dynamicHeart.setOnClickListener(imageListener("heart", viewholder.dynamicHeart, position));
		viewholder.dynamicComments.setOnClickListener(imageListener("comments", viewholder.dynamicComments, position));
		viewholder.dynamicShare.setOnClickListener(imageListener("share", viewholder.dynamicShare, position));
		switch (dynamicType) {
		case 0:
			viewholder.dynamicPhoto.setImageResource(viewEntity.get(position).dynamicPhoto);
			viewholder.dynamicPhoto.setLayoutParams(new RelativeLayout.LayoutParams(windowswidth, windowswidth));
			viewholder.dynamicPhoto.setOnClickListener(imageListener("photo", viewholder.dynamicPhoto, position));
			break;
		case 1:
			viewholder.activityPhoto.setImageResource(viewEntity.get(position).activityPhoto);
			viewholder.activityPhoto.setLayoutParams(new RelativeLayout.LayoutParams(windowswidth, windowswidth/2));
			viewholder.activityPhoto.setOnClickListener(imageListener("photo", viewholder.activityPhoto, position));
			viewholder.activityTitle.setText(viewEntity.get(position).activityTitle);
			viewholder.activityTime.setText(viewEntity.get(position).activityTime);
			viewholder.activityAddress.setText(viewEntity.get(position).activityAddress);
			break;
		default:
			
		}
		return convertView;
	}
	public OnClickListener imageListener(final String tag, final ImageView img, final int position) {
		OnClickListener listen = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (tag) {
				case "head":
					Intent intent1 = new Intent(context, AccountActivity.class);
					intent1.putExtra("name", viewEntity.get(position).userName.toString());
					intent1.putExtra("headimg", viewEntity.get(position).userHead);
					if (viewEntity.get(position).userName.equals("Wchuan")) {
						intent1.putExtra("accountid", 0);
					} else {
						intent1.putExtra("accountid", 1);
					}
					context.startActivity(intent1);
					break;
				case "photo":
					Intent intent2 = new Intent(context, DynamicDetailView.class);
					Bundle bundle = new Bundle();
					bundle.putInt("pageItem", position);
					bundle.putSerializable("viewEntity", (Serializable) viewEntity);
					intent2.putExtras(bundle);
					context.startActivity(intent2);
					break;
				case "heart":
					img.setImageResource(R.drawable.icon_heart_red);
					break;
				case "comments":
					img.setImageResource(R.drawable.chat_btn_sl);
					break;
				case "share":
					img.setImageResource(R.drawable.icon_shared);
					break;
				default:
					break;
				}
			}
		};
		return listen;
	};

	public OnClickListener normalListener(final String num) {
		OnClickListener listen = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (num) {
				case "head":
				case "heart":
				case "comments":
				case "share":
				default:
				}
			}
		};
		return listen;
	};
}
