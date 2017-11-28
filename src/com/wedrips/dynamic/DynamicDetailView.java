package com.wedrips.dynamic;

import java.util.ArrayList;
import java.util.List;

import com.wedrips.R;
import com.wedrips.adapter.DynamicViewAdapter;
import com.wedrips.plus.ExitAppActivity;
import com.wedrips.view.PhotoAnimationView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;

public class DynamicDetailView extends FragmentActivity {

	private ViewPager viewpager;
	private DynamicViewAdapter adapter;
	private List<Fragment> viewGroup = new ArrayList<Fragment>();// 以后别忘了要初始化
	private List<DynamicValueEntity> viewEntity = new ArrayList<DynamicValueEntity>();// 动态数据数组
	private PhotoAnimationView mPhotoView;
	private int preItem;
	private int pageItem = 0;
	private int nextItem;
	private int windowswidth;// 屏幕像素宽度
	private TextView dynamicTitle;

	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dynamic_detail_layout);
		ExitAppActivity.getInstance().addActivity(this);// 加入Activity栈
		windowswidth = getResources().getDisplayMetrics().widthPixels;
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		dynamicTitle=(TextView) findViewById(R.id.dynamic_title);
		
		Bundle bundle = getIntent().getExtras(); // 从Intent获取动态数据
		viewEntity = (List<DynamicValueEntity>) bundle.getSerializable("viewEntity");
		pageItem = bundle.getInt("pageItem");
		
		initView(viewEntity);// 配置初始数据
		adapter = new DynamicViewAdapter(getSupportFragmentManager(), viewGroup, windowswidth);// 为适配器传参
		viewpager.setAdapter(adapter);
		viewpager.setCurrentItem(pageItem);// setCurrentItem必须在setAdapter之后
		viewpager.addOnPageChangeListener(new PagerListener());
		dynamicTitle.setText(viewEntity.get(pageItem).activityTitle);
		
		findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		findViewById(R.id.set_btn).setOnClickListener(null);
	}

	public void initView(List<DynamicValueEntity> viewEntity) {
		Fragment frag = null;
		for (DynamicValueEntity entity : viewEntity) {
			switch (entity.dynamicType) {
			case 0:
				frag = new FragmentDynamicDetail(entity);
				break;
			case 1:
				frag = new FragmentActivityDetail(entity);
				break;
			default:
			}
			viewGroup.add(frag);
		}
	}

	class PagerListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			dynamicTitle.setText(viewEntity.get(arg0).activityTitle);
		}
	}
}