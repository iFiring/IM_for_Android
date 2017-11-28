package com.wedrips.chat;

public class ChatListEntity{ //聊天列表的数据类
	
	public int imgid;
	public String name;
	public String time;
	
	public ChatListEntity( int imgid,String name,String time){
		this.imgid=imgid;
		this.name=name;
		this.time=time;
	}
}
