package com.wedrips.account;

import java.util.ArrayList;
import java.util.List;

import com.wedrips.R;
import com.wedrips.adapter.FragmentSlideAdapter;
import com.wedrips.dynamic.DynamicValueEntity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class AccountFragment extends Fragment {
	private ViewPager accountviewpager;
	private FragmentSlideAdapter adapter;
	private ImageView accountdynamicbtn, accountactivitybtn, accountdetailbtn;
	public List<DynamicValueEntity> viewEntity = new ArrayList<DynamicValueEntity>();// 动态数据数组
	public int[] imgs= new int[] {R.color.color_green, R.color.color_blue, R.color.color_pink, R.color.yellow, R.color.red};
	private AccountFragDynamics accountfragdynamic;
	private AccountFragActivity accountfragactivity;
	private AccountFragDetails accountfragdetail;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceStete) {
		View root = inflater.inflate(R.layout.account_page, container, false);
		initEntity(viewEntity);// 初始化数据
		accountdynamicbtn = (ImageView) root.findViewById(R.id.account_tabbtn_dynamic);
		accountactivitybtn = (ImageView) root.findViewById(R.id.account_tabbtn_activity);
		accountdetailbtn = (ImageView) root.findViewById(R.id.account_tabbtn_detail);
		accountviewpagerfun(root);// 初始化所有控件
		accountviewpager.setCurrentItem(0);// 初始化选中页面
		root.findViewById(R.id.search_btn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "你点击了搜索按钮", Toast.LENGTH_SHORT).show();
			}
		});
		accountdynamicbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				accountviewpager.setCurrentItem(0);
			}
		});
		accountactivitybtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				accountviewpager.setCurrentItem(1);
			}
		});
		accountdetailbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				accountviewpager.setCurrentItem(2);
			}
		});
		root.findViewById(R.id.account_set).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), AccountSetActivity.class));
			}
		});
		return root;
	}

	private void accountviewpagerfun(View root) {
		accountviewpager = (ViewPager) root.findViewById(R.id.account_container);
		accountviewpager.setOffscreenPageLimit(3); // 这个很重要,设置3个界面来回切换不会重新加载
		List<Fragment> data = new ArrayList<Fragment>();

		accountfragdynamic = new AccountFragDynamics(viewEntity,imgs);
		accountfragactivity = new AccountFragActivity();
		accountfragdetail = new AccountFragDetails();

		data.add(accountfragdynamic);
		data.add(accountfragactivity);
		data.add(accountfragdetail);

		adapter = new FragmentSlideAdapter(getFragmentManager(), data);
		accountviewpager.setAdapter(adapter); // 应用适配器
		accountviewpager.addOnPageChangeListener(accountpagechangelistener); // 监听界面滑动
	}

	// 滑动监听
	private OnPageChangeListener accountpagechangelistener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int index) {
			switch (index) {
			case 0:
				accountdynamicbtn.setImageResource(R.drawable.account_dynamics_sl);
				accountdetailbtn.setImageResource(R.drawable.account_details);
				accountactivitybtn.setImageResource(R.drawable.account_activity);
				break;
			case 1:
				accountdynamicbtn.setImageResource(R.drawable.account_dynamics);
				accountdetailbtn.setImageResource(R.drawable.account_details);
				accountactivitybtn.setImageResource(R.drawable.account_activity_sl);
				break;
			case 2:
				accountdynamicbtn.setImageResource(R.drawable.account_dynamics);
				accountdetailbtn.setImageResource(R.drawable.account_details_sl);
				accountactivitybtn.setImageResource(R.drawable.account_activity);
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

	public void initEntity(List<DynamicValueEntity> viewEntity) {
		viewEntity.add(new DynamicValueEntity( R.color.color_green,"微滴", "5:20", "丰富线下生活！",R.color.color_green,0,0,0,null,0));
		viewEntity.add(new DynamicValueEntity( R.color.color_blue,"微滴", "5:20", "因梦想而不甘平庸！", R.color.color_blue,0,0,0,null,0));
		viewEntity.add(new DynamicValueEntity( R.color.color_pink,"微滴", "5:20", "丰富线下生活！", R.color.color_pink,0,0,0,null,0));
		viewEntity.add(new DynamicValueEntity( R.color.yellow,"微滴", "5:20", "因梦想而不甘平庸！",R.color.yellow,0,0,0,null,0));
		viewEntity.add(new DynamicValueEntity( R.color.red,"微滴", "5:20", "丰富线下生活！", R.color.red_french,0,0,0,null,0));
	}
}