package com.wedrips.chat;

import com.wedrips.R;
import com.wedrips.adapter.ChatListAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ChatFragFriend extends Fragment {
	private ListView lv;
	private ChatListAdapter adapter;
	private ChatListEntity[] data=new ChatListEntity[]{
			new ChatListEntity(R.drawable.beauty_square,"Beauty","5:20"),
			new ChatListEntity(R.drawable.head,"Wchuan","2:50")};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root=(View) inflater.inflate(R.layout.chat_listview, null);
		lv=(ListView)root.findViewById(R.id.chat_tab_friend_listview);
		adapter=new ChatListAdapter(getActivity(),data);
		lv.setAdapter(adapter);

		return root;
	}
}