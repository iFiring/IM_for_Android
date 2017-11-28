package com.wedrips.chat;

import com.wedrips.R;
import com.wedrips.adapter.ChatListAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ChatFragActivity extends Fragment {
	
	private ListView lv;
	private ChatListAdapter adapter;
	private ChatListEntity[] data=new ChatListEntity[]{
			new ChatListEntity(R.color.red_french,"江西财经大学第十届运动会","10:00"),
			new ChatListEntity(R.color.red_french,"全国大学哇哈哈营销大赛","16:00"),
			new ChatListEntity(R.color.red_french,"土匪的生日小聚会","17:00")};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root=(View) inflater.inflate(R.layout.chat_listview, null);
		lv=(ListView)root.findViewById(R.id.chat_tab_friend_listview);
		adapter=new ChatListAdapter(getActivity(),data);
		lv.setAdapter(adapter);
		
		return root;
	}
}