package com.wedrips.dynamic;

import java.io.Serializable;

public class DynamicValueEntity implements Serializable {
	public String userName;
	public int userHead;
	public int dynamicPhoto;
	public String Time;
	public String Text;
	public int Heart;
	public int Comments;
	public int Share;
	
	public int activityPhoto;
	public String activityTitle="动态详情";
	public String activityTime;
	public String activityAddress;

	public int dynamicType = 0;
	public String isFromDynamic = null;// 动态是否来自转发
	public DynamicValueEntity(int userHead, String userName, String Time, String Text, int Photo,
			int Heart, int Comments, int Share, String isFromDynamic, int dynamicType) {
		this.userHead = userHead;
		this.userName = userName;
		this.Time = Time;
		this.Text = Text;
		this.dynamicPhoto = Photo;
		this.Heart = Heart;
		this.Comments = Comments;
		this.Share = Share;
		this.isFromDynamic = isFromDynamic;
		this.dynamicType = dynamicType;
	}

	public DynamicValueEntity(int userHead, String userName, String Time, String Text, int activityPhoto,String activityTitle,
			String activityTime,String activityAddress,int Heart, int Comments, int Share, String isFromDynamic, int dynamicType) {
		this.userHead = userHead;
		this.userName = userName;
		this.Time = Time;
		this.Text = Text;
		this.activityPhoto = activityPhoto;
		this.activityTitle=activityTitle;
		this.activityTime=activityTime;
		this.activityAddress=activityAddress;
		this.Heart = Heart;
		this.Comments = Comments;
		this.Share = Share;
		this.isFromDynamic = isFromDynamic;
		this.dynamicType = dynamicType;
	}

	public DynamicValueEntity() {

	}
}