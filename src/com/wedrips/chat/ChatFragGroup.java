package com.wedrips.chat;

import com.wedrips.R;
import com.wedrips.adapter.ChatListAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

//Chat的ListViewFragment列表
public class ChatFragGroup extends Fragment {
	
	private ListView lv;
	private ChatListAdapter adapter;
	private ChatListEntity[] data=new ChatListEntity[]{
			new ChatListEntity(R.color.color_green_french,"江西财经大学","10:00"),
			new ChatListEntity(R.color.color_green_french,"永远的高三一班","9:00"),
			new ChatListEntity(R.color.color_green_french,"江财羽毛球协会","6:00")};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root=(View) inflater.inflate(R.layout.chat_listview, null);
		lv=(ListView)root.findViewById(R.id.chat_tab_friend_listview);
		adapter=new ChatListAdapter(getActivity(),data);
		lv.setAdapter(adapter);
		
		return root;
	}
}