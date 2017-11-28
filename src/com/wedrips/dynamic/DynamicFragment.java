package com.wedrips.dynamic;

import java.util.ArrayList;
import java.util.List;

import com.wedrips.MainActivity;
import com.wedrips.R;
import com.wedrips.R.id;
import com.wedrips.R.layout;
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
import android.widget.TextView;
import android.widget.Toast;

public class DynamicFragment extends Fragment {
	public ViewPager dynamicviewpager;
	private FragmentSlideAdapter adapter;
	public TextView dynamicfriendbtn, dynamicfollowbtn, dynamicnearbtn;
	private DynamicFragFriend dynamicfragfriend;
	private DynamicFragFollow dynamicfragfollow;
	private DynamicFragNear dynamicfragnear;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceStete) {
		View root = inflater.inflate(R.layout.dynamic_page, container, false);
		dynamicfriendbtn = (TextView) root.findViewById(R.id.friend_btn);
		dynamicfollowbtn = (TextView) root.findViewById(R.id.follow_btn);
		dynamicnearbtn = (TextView) root.findViewById(R.id.near_btn);
		dynamicviewpagerfun(root);
		dynamicviewpager.setCurrentItem(1);
		root.findViewById(R.id.search_btn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "你点击了搜索按钮", Toast.LENGTH_SHORT).show();
			}
		});
		dynamicfriendbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dynamicviewpager.setCurrentItem(0);
			}
		});
		dynamicfollowbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dynamicviewpager.setCurrentItem(1);
			}
		});
		dynamicnearbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dynamicviewpager.setCurrentItem(2);
			}
		});
		root.findViewById(R.id.atme_btn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), DynamicAtActivity.class));
			}
		});
		root.findViewById(R.id.btn_plus).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), NewDynamicFragAct.class));
			}
		});
		return root;
	}

	public void dynamicviewpagerfun(View root) {
		dynamicviewpager = (ViewPager) root.findViewById(R.id.dynamicpage_container);
		dynamicviewpager.setOffscreenPageLimit(3); // 这个很重要,设置3个界面来回切换不会重新加载
		List<Fragment> data = new ArrayList<Fragment>();

		dynamicfragfriend = new DynamicFragFriend();
		dynamicfragfollow = new DynamicFragFollow();
		dynamicfragnear = new DynamicFragNear();

		data.add(dynamicfragfriend);
		data.add(dynamicfragfollow);
		data.add(dynamicfragnear);

		adapter = new FragmentSlideAdapter(getFragmentManager(), data);
		dynamicviewpager.setAdapter(adapter); // 应用适配器
		dynamicviewpager.addOnPageChangeListener(dynamicpagechangelistener); // 监听界面滑动
	}

	private OnPageChangeListener dynamicpagechangelistener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int index) {
			switch (index) {
			case 0:
				dynamicfriendbtn.setTextColor(Color.parseColor("#0099FF"));
				dynamicfriendbtn.setBackgroundResource(R.drawable.shape_whitebtn);
				dynamicfollowbtn.setTextColor(Color.WHITE);
				dynamicfollowbtn.setBackgroundResource(R.drawable.transparent);
				dynamicnearbtn.setTextColor(Color.WHITE);
				dynamicnearbtn.setBackgroundResource(R.drawable.transparent);
				break;
			case 1:
				dynamicfriendbtn.setTextColor(Color.WHITE);
				dynamicfriendbtn.setBackgroundResource(R.drawable.transparent);
				dynamicfollowbtn.setTextColor(Color.parseColor("#0099FF"));
				dynamicfollowbtn.setBackgroundResource(R.drawable.shape_whitebtn);
				dynamicnearbtn.setTextColor(Color.WHITE);
				dynamicnearbtn.setBackgroundResource(R.drawable.transparent);
				break;
			case 2:
				dynamicfriendbtn.setTextColor(Color.WHITE);
				dynamicfriendbtn.setBackgroundResource(R.drawable.transparent);
				dynamicfollowbtn.setTextColor(Color.WHITE);
				dynamicfollowbtn.setBackgroundResource(R.drawable.transparent);
				dynamicnearbtn.setTextColor(Color.parseColor("#0099FF"));
				dynamicnearbtn.setBackgroundResource(R.drawable.shape_whitebtn);
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