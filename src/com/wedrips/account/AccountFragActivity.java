package com.wedrips.account;

import java.util.ArrayList;
import java.util.List;

import com.wedrips.R;
import com.wedrips.adapter.DynamicListAdapter;
import com.wedrips.dynamic.DynamicValueEntity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AccountFragActivity extends Fragment {
	public int pageItem = 1;// 选取的定位页
	public ListView lv;
	public List<DynamicValueEntity> viewEntity = new ArrayList<DynamicValueEntity>();// 动态数据数组
	public DynamicListAdapter adapter;
	public int windowswidth;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.dynamic_tab_follow, null);
		windowswidth = getResources().getDisplayMetrics().widthPixels;
		lv=(ListView) root.findViewById(R.id.dynamic_tab_follow_listview);
		initEntity(viewEntity);// 初始化数据
		adapter=new DynamicListAdapter(getActivity(),viewEntity,windowswidth);
		lv.setAdapter(adapter);
		lv.setSelector(new ColorDrawable(Color.TRANSPARENT));// 屏蔽ListView默认点击效果
		return root;
	}
	public void initEntity(List<DynamicValueEntity> viewEntity) {
		viewEntity.add(new DynamicValueEntity( R.drawable.wedrips,"微滴","5:20", "因梦想而不甘平庸！", R.color.red_french,"丰富线下生活！","5:20","江西北大科技园·A座",0,0,0,null,1));
		viewEntity.add(new DynamicValueEntity( R.drawable.jxufe,"江西财经大学","5:20", "江财第十届运动会，青春肆射！", R.color.color_green,"第十届运动会","7:40","江财体育馆",0,0,0,null,1));
		viewEntity.add(new DynamicValueEntity( R.drawable.jxufe,"江财学生会","5:20", "让我们拥抱春天！", R.color.color_blue,"新春初行","8:00","梅岭国家公园",0,0,0,null,1));
		viewEntity.add(new DynamicValueEntity( R.drawable.head_yankai,"颜凯","5:20", "欢迎参加我的生日聚会！", R.color.yellow,"生日聚会","21:20","麦霸KTV",0,0,0,null,1));
	}
}