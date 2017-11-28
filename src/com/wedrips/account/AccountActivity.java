package com.wedrips.account;

import java.util.ArrayList;
import java.util.List;

import com.wedrips.R;
import com.wedrips.adapter.FragmentSlideAdapter;
import com.wedrips.dynamic.DynamicValueEntity;
import com.wedrips.plus.ExitAppActivity;
import com.wedrips.view.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountActivity extends FragmentActivity {
	private ViewPager accountviewpager;
	private FragmentSlideAdapter adapter;
	private ImageView accountdynamicbtn,accountactivitybtn,accountdetailbtn;
	public List<DynamicValueEntity> viewEntity = new ArrayList<DynamicValueEntity>();// 动态数据数组
	public int[] imgs= new int[] { R.color.color_green, R.color.color_blue, R.color.color_pink, R.color.yellow, R.color.red_french};
	private AccountFragDynamics accountfragdynamic;private AccountFragActivity accountfragactivity;private AccountFragDetails accountfragdetail;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_page_others);
		ExitAppActivity.getInstance().addActivity(this);//加入Activity栈
		initEntity(viewEntity);// 初始化数据
		
		Bundle bundle=getIntent().getExtras();//从Intent获取数据，并判断是否为本账号
		if(bundle.getInt("accountid")==0){
			findViewById(R.id.focus_btn).setVisibility(View.GONE);
		}
		//为账号页设置属性
		TextView name=(TextView) findViewById(R.id.AccountName);
		CircleImageView head=(CircleImageView) findViewById(R.id.headimg);
		name.setText(bundle.getString("name"));
		head.setImageResource(bundle.getInt("headimg"));
		
		accountdynamicbtn= (ImageView) findViewById(R.id.account_tabbtn_dynamic);
		accountactivitybtn=(ImageView) findViewById(R.id.account_tabbtn_activity);
		accountdetailbtn= (ImageView) findViewById(R.id.account_tabbtn_detail);
		accountviewpagerfun();//初始化所有控件
		accountviewpager.setCurrentItem(0);// 初始化选中页面
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
		findViewById(R.id.account_set).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(AccountActivity.this,AccountSetActivity.class));
			}
		});
		findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	public void onBackPressed(){
		finish();
	}
	private void accountviewpagerfun() {
		accountviewpager = (ViewPager) findViewById(R.id.account_container);
		accountviewpager.setOffscreenPageLimit(3);		//这个很重要,设置3个界面来回切换不会重新加载
		List<Fragment> data = new ArrayList<Fragment>();

		accountfragdynamic = new AccountFragDynamics(viewEntity,imgs);
		accountfragactivity =new AccountFragActivity();
		accountfragdetail = new AccountFragDetails();

		data.add(accountfragdynamic);data.add(accountfragactivity);data.add(accountfragdetail);

		adapter = new FragmentSlideAdapter(getSupportFragmentManager(), data);
		accountviewpager.setAdapter(adapter); 								// 应用适配器
		accountviewpager.addOnPageChangeListener(accountpagechangelistener);	// 监听界面滑动
	}
	//滑动监听
	private OnPageChangeListener accountpagechangelistener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int index) {
			switch (index){
				case 0:accountdynamicbtn.setImageResource(R.drawable.account_dynamics_sl);
					accountdetailbtn.setImageResource(R.drawable.account_details);
					accountactivitybtn.setImageResource(R.drawable.account_activity);
					break;
				case 1:accountdynamicbtn.setImageResource(R.drawable.account_dynamics);
					accountdetailbtn.setImageResource(R.drawable.account_details);
					accountactivitybtn.setImageResource(R.drawable.account_activity_sl);
					break;
				case 2:accountdynamicbtn.setImageResource(R.drawable.account_dynamics);
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
