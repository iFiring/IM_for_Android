package com.wedrips.dynamic;

import com.wedrips.view.PhotoAnimationView;

import android.graphics.Shader;
import android.widget.ImageView;
import android.widget.TextView;

public final class DynamicViewHolder {

	public ImageView userHead;
	public TextView userName;
	public TextView dynamicTime;
	public TextView dynamicText;
	public ImageView dynamicHeart;
	public ImageView dynamicComments;
	public ImageView dynamicShare;

	public PhotoAnimationView dynamicPhoto;

	public ImageView activityPhoto;
	public TextView activityTitle;
	public TextView activityTime;
	public TextView activityAddress;

	public boolean isFromDynamic = false;// 动态是否来自转发

	// public DynamicViewHolder(TextView userName, ImageView userHead,
	// PhotoAnimationView dynamicPhoto, TextView dynamicTime,
	// TextView dynamicText, ImageView dynamicHeart, ImageView dynamicComments,
	// ImageView dynamicShare) {
	// this.userName = userName;
	// this.userHead = userHead;
	// this.dynamicPhoto = dynamicPhoto;
	// this.dynamicTime = dynamicTime;
	// this.dynamicText = dynamicText;
	// this.dynamicHeart=dynamicHeart;
	// this.dynamicPhoto=dynamicPhoto;
	// this.dynamicShare=dynamicShare;
	// }

	public DynamicViewHolder() {
		// TODO Auto-generated constructor stub
	}

}
