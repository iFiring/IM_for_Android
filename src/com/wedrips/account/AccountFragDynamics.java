package com.wedrips.account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wedrips.R;
import com.wedrips.dynamic.DynamicDetailView;
import com.wedrips.dynamic.DynamicValueEntity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

//Account的ListViewFragment列表
public class AccountFragDynamics extends Fragment {
	
	public GridView gridview;
	public int windowswidth;
	public List<DynamicValueEntity> viewEntity = new ArrayList<DynamicValueEntity>();// 动态数据数组
	public int[] imgs;
	public AccountFragDynamics(List<DynamicValueEntity> viewEntity,int[] imgs){
		this.viewEntity=viewEntity;
		this.imgs=imgs;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.account_tab_dynamic, null);
		windowswidth = getResources().getDisplayMetrics().widthPixels;
		
		gridview = (GridView) root.findViewById(R.id.account_dynamic_grid);
		gridview.setLayoutParams(new FrameLayout.LayoutParams(windowswidth,FrameLayout.LayoutParams.WRAP_CONTENT));
		gridview.setAdapter(new BaseAdapter() {
			@Override
			public int getCount() {
				return imgs.length;
			}
			@Override
			public Object getItem(int position) {
				return null;
			}
			@Override
			public long getItemId(int position) {
				return 0;
			}
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ImageView img=new ImageView(getActivity());
				img.setLayoutParams(new AbsListView.LayoutParams(windowswidth / 3,windowswidth/3));
				img.setScaleType(ImageView.ScaleType.CENTER_CROP);
				img.setImageResource(imgs[position]);
				return img;
			}
		});

		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), DynamicDetailView.class);
				Bundle bundle = new Bundle();
				bundle.putInt("pageItem", position);
				bundle.putSerializable("viewEntity",(Serializable) viewEntity);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));// 屏蔽GridView默认点击效果
		return root;
	}
	
}