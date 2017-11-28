package com.wedrips.dynamic;

import java.util.ArrayList;
import java.util.List;

import com.wedrips.R;
import com.wedrips.account.AccountActivity;
import com.wedrips.account.AccountFragActivity;
import com.wedrips.account.AccountFragDetails;
import com.wedrips.account.AccountFragDynamics;
import com.wedrips.adapter.FragmentSlideAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentActivityDetail extends Fragment {

	private DynamicValueEntity entity;
	private DynamicViewHolder viewholder;
	private int windowswidth;
	private ViewPager viewPager;
	private FragmentSlideAdapter adapter;
	private ImageView accountdynamicbtn, accountdetailbtn;
	public List<DynamicValueEntity> viewEntity = new ArrayList<DynamicValueEntity>();// 动态数据数组
	public int[] imgs= new int[] {R.color.color_green, R.color.color_blue, R.color.color_pink, R.color.yellow, R.color.red};
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root=inflater.inflate(R.layout.activity_layout_detail, container, false);
		viewholder = new DynamicViewHolder();
		windowswidth = getResources().getDisplayMetrics().widthPixels;
		initEntity(viewEntity);// 初始化数据
		viewPager=(ViewPager) root.findViewById(R.id.viewpager);
		viewholder.userHead = (ImageView) root.findViewById(R.id.activityImg);
		viewholder.activityTime=(TextView) root.findViewById(R.id.activitytime);
		viewholder.activityAddress=(TextView) root.findViewById(R.id.activityaddress);
		viewholder.dynamicHeart = (ImageView) root.findViewById(R.id.heart);
		viewholder.dynamicComments = (ImageView) root.findViewById(R.id.comments);
		viewholder.dynamicShare = (ImageView) root.findViewById(R.id.share);
		accountdynamicbtn = (ImageView) root.findViewById(R.id.dynamicbtn);
		accountdetailbtn = (ImageView) root.findViewById(R.id.detailbtn);
		
		viewholder.userHead.setImageResource(entity.userHead);
		viewholder.activityTime.setText(entity.activityTime);
		viewholder.activityAddress.setText(entity.activityAddress);
		
		viewholder.dynamicHeart.setOnClickListener(Listener("heart", entity, viewholder));
		viewholder.dynamicComments.setOnClickListener(Listener("comments", entity, viewholder));
		viewholder.dynamicShare.setOnClickListener(Listener("share", entity, viewholder));

		accountviewpagerfun();// 初始化所有控件
		viewPager.setCurrentItem(0);// 初始化选中页面
		accountdynamicbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				viewPager.setCurrentItem(0);
			}
		});
		accountdetailbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				viewPager.setCurrentItem(1);
			}
		});
		return root;
	}
	
	public FragmentActivityDetail(DynamicValueEntity entity) {
		this.entity=entity;
	}
	private void accountviewpagerfun() {
		viewPager.setOffscreenPageLimit(2); // 这个很重要,设置2个界面来回切换不会重新加载
		List<Fragment> data = new ArrayList<Fragment>();

		data.add(new AccountFragDynamics(viewEntity,imgs));
		data.add(new AccountFragDetails());

		adapter = new FragmentSlideAdapter(getChildFragmentManager(), data);
		viewPager.setAdapter(adapter); // 应用适配器
		viewPager.addOnPageChangeListener(accountpagechangelistener); // 监听界面滑动
	}
	// 滑动监听
	private OnPageChangeListener accountpagechangelistener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int index) {
			switch (index) {
			case 0:
				accountdynamicbtn.setImageResource(R.drawable.account_dynamics_sl);
				accountdetailbtn.setImageResource(R.drawable.account_details);
				break;
			case 1:
				accountdynamicbtn.setImageResource(R.drawable.account_dynamics);
				accountdetailbtn.setImageResource(R.drawable.account_details_sl);
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
	public void initEntity(List<DynamicValueEntity> viewEntity) {
		viewEntity.add(new DynamicValueEntity( R.color.color_green,"微滴", "5:20", "丰富线下生活！",R.color.color_green,0,0,0,null,0));
		viewEntity.add(new DynamicValueEntity( R.color.color_blue,"微滴", "5:20", "因梦想而不甘平庸！", R.color.color_blue,0,0,0,null,0));
		viewEntity.add(new DynamicValueEntity( R.color.color_pink,"微滴", "5:20", "丰富线下生活！", R.color.color_pink,0,0,0,null,0));
		viewEntity.add(new DynamicValueEntity( R.color.yellow,"微滴", "5:20", "因梦想而不甘平庸！",R.color.yellow,0,0,0,null,0));
		viewEntity.add(new DynamicValueEntity( R.color.red,"微滴", "5:20", "丰富线下生活！", R.color.red_french,0,0,0,null,0));
	}
}
