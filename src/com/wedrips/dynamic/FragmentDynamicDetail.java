package com.wedrips.dynamic;

import com.wedrips.R;
import com.wedrips.account.AccountActivity;
import com.wedrips.view.PhotoAnimationView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class FragmentDynamicDetail extends Fragment {

	DynamicValueEntity entity;
	DynamicViewHolder viewholder;
	private int windowswidth;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root=inflater.inflate(R.layout.dynamic_layout, null);
		viewholder = new DynamicViewHolder();
		windowswidth = getResources().getDisplayMetrics().widthPixels;
		viewholder.userName = (TextView) root.findViewById(R.id.name);
		viewholder.dynamicTime = (TextView) root.findViewById(R.id.time);
		viewholder.dynamicText = (TextView) root.findViewById(R.id.text);
		viewholder.userHead = (ImageView) root.findViewById(R.id.head);
		viewholder.dynamicPhoto = (PhotoAnimationView) root.findViewById(R.id.photo);
		viewholder.dynamicHeart = (ImageView) root.findViewById(R.id.heart);
		viewholder.dynamicComments = (ImageView) root.findViewById(R.id.comments);
		viewholder.dynamicShare = (ImageView) root.findViewById(R.id.share);
		
		viewholder.dynamicPhoto.setImageResource(entity.dynamicPhoto);
		viewholder.dynamicPhoto.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, windowswidth));
		viewholder.dynamicPhoto.enable();
		
		viewholder.userHead.setImageResource(entity.userHead);
		viewholder.userName.setText(entity.userName);
		viewholder.dynamicTime.setText(entity.Time);
		viewholder.dynamicText.setText(entity.Text);
		
		viewholder.userHead.setOnClickListener(Listener("head", entity, viewholder));
		viewholder.userName.setOnClickListener(Listener("head", entity, viewholder));
		viewholder.dynamicTime.setOnClickListener(null);
		viewholder.dynamicText.setOnClickListener(null);
		viewholder.dynamicHeart.setOnClickListener(Listener("heart", entity, viewholder));
		viewholder.dynamicComments.setOnClickListener(Listener("comments", entity, viewholder));
		viewholder.dynamicShare.setOnClickListener(Listener("share", entity, viewholder));
		return root;
	}
	public FragmentDynamicDetail(DynamicValueEntity entity) {
		this.entity=entity;
	}
	public OnClickListener Listener(final String tag, final DynamicValueEntity entity,final DynamicViewHolder viewholder) {
		OnClickListener listen = new View.OnClickListener() {
			public void onClick(View v) {
				switch (tag) {
				case "head":
					Intent intent = new Intent(getActivity(), AccountActivity.class);
					intent.putExtra("name", entity.userName.toString());
					intent.putExtra("headimg", entity.userHead);
					System.out.println(entity.userName.toString());
					if (entity.userName.equals("Wchuan")) {
						intent.putExtra("accountid", 0);
						System.out.println(0);
					} else {
						intent.putExtra("accountid", 1);
						System.out.println(1);
					}
					startActivity(intent);
					break;
				case "photo":
					break;
				case "heart":
					viewholder.dynamicHeart.setImageResource(R.drawable.icon_heart_red);
					break;
				case "comments":
					viewholder.dynamicComments.setImageResource(R.drawable.chat_btn_sl);
					break;
				case "share":
					viewholder.dynamicShare.setImageResource(R.drawable.icon_shared);
					break;
				default:
					break;
				}
			}
		};
		return listen;
	}
}
