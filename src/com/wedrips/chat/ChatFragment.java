package com.wedrips.chat;

import java.util.ArrayList;
import java.util.List;

import com.wedrips.R;
import com.wedrips.adapter.FragmentSlideAdapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChatFragment extends Fragment{
	private ViewPager chatviewpager;
	private FragmentSlideAdapter adapter;
	private TextView chatgroupbtn,chatfriendbtn,chatactivitybtn;
	private ImageView search; private ImageView contactslist;
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceStete){
		View root=inflater.inflate(R.layout.chat_fragment,container,false);
		chatgroupbtn=(TextView) root.findViewById(R.id.group_btn);
		chatfriendbtn=(TextView) root.findViewById(R.id.friend_btn);
		chatactivitybtn=(TextView) root.findViewById(R.id.activity_btn);
		search=(ImageView) root.findViewById(R.id.search_btn);
		contactslist=(ImageView) root.findViewById(R.id.contactslist_btn);
		chatviewpagerfun(root);
		chatviewpager.setCurrentItem(1); 
		root.findViewById(R.id.search_btn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "你点击了搜索按钮", Toast.LENGTH_SHORT).show();
			}
		});
		chatgroupbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				chatviewpager.setCurrentItem(0); 
			}
		});
		chatfriendbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				chatviewpager.setCurrentItem(1); 
			}
		});
		chatactivitybtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				chatviewpager.setCurrentItem(2); 
			}
		});
		contactslist.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(),ContactsList.class));
			}
		});
		return root;
	}

	public void chatviewpagerfun(View root) {
		chatviewpager = (ViewPager) root.findViewById(R.id.chatpage_container);
		chatviewpager.setOffscreenPageLimit(3);		//这个很重要,设置3个界面来回切换不会重新加载
		List<Fragment> data = new ArrayList<Fragment>();

		data.add(new ChatFragGroup());
		data.add(new ChatFragFriend());
		data.add(new ChatFragActivity());

		adapter = new FragmentSlideAdapter(getFragmentManager(), data);
		chatviewpager.setAdapter(adapter); 								// 应用适配器
		chatviewpager.addOnPageChangeListener(chatpagechangelistener);	// 监听界面滑动
	}
	private OnPageChangeListener chatpagechangelistener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int index) {
			switch (index){
				case 0:chatgroupbtn.setTextColor(Color.parseColor("#0099FF"));chatgroupbtn.setBackgroundResource(R.drawable.shape_whitebtn);
					chatfriendbtn.setTextColor(Color.parseColor("#FFFFFF"));chatfriendbtn.setBackgroundResource(R.drawable.transparent);
					chatactivitybtn.setTextColor(Color.parseColor("#FFFFFF"));chatactivitybtn.setBackgroundResource(R.drawable.transparent);
					break;
				case 1:chatgroupbtn.setTextColor(Color.parseColor("#FFFFFF"));chatgroupbtn.setBackgroundResource(R.drawable.transparent);
				chatfriendbtn.setTextColor(Color.parseColor("#0099FF"));chatfriendbtn.setBackgroundResource(R.drawable.shape_whitebtn);
				chatactivitybtn.setTextColor(Color.parseColor("#FFFFFF"));chatactivitybtn.setBackgroundResource(R.drawable.transparent);
					break;
				case 2:chatgroupbtn.setTextColor(Color.parseColor("#FFFFFF"));chatgroupbtn.setBackgroundResource(R.drawable.transparent);
				chatfriendbtn.setTextColor(Color.parseColor("#FFFFFF"));chatfriendbtn.setBackgroundResource(R.drawable.transparent);
				chatactivitybtn.setTextColor(Color.parseColor("#0099FF"));chatactivitybtn.setBackgroundResource(R.drawable.shape_whitebtn);
					break;
			}
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};
}