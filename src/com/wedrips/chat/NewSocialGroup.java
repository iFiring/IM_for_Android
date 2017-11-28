package com.wedrips.chat;

import java.util.ArrayList;
import java.util.List;

import com.wedrips.R;
import com.wedrips.adapter.FragmentSlideAdapter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NewSocialGroup extends FragmentActivity {
	private ViewPager newsocialgroupviewpager; private FragmentSlideAdapter adapter;
	private TextView newgrouptabbtn,newpublictabbtn;
	private ImageView backbtn,confirmbtn;
	private NewGroupTab newgrouptab;private NewPublicTab newpublictab;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_socialgroup_layout);
		newgrouptabbtn=(TextView) findViewById(R.id.newgroup_btn);
		newpublictabbtn=(TextView) findViewById(R.id.newpublic_btn);
		backbtn=(ImageView) findViewById(R.id.back_btn);
		confirmbtn=(ImageView) findViewById(R.id.confirm_btn);
		viewpagerfun();
		newsocialgroupviewpager.setCurrentItem(0);
		newgrouptabbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				newsocialgroupviewpager.setCurrentItem(0); 
			}
		});
		newpublictabbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				newsocialgroupviewpager.setCurrentItem(1); 
			}
		});
	}
	public class NewGroupTab extends Fragment{
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.new_group_ed, null);
		}
	}
	public class NewPublicTab extends Fragment{
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.new_public_ed, null);
		}
	}
	public void viewpagerfun(){
		newsocialgroupviewpager=(ViewPager) findViewById(R.id.new_socialgroup_container);
		newsocialgroupviewpager.setOffscreenPageLimit(2);
		List<Fragment> data=new ArrayList<Fragment>();
		newgrouptab=new NewGroupTab();
		newpublictab=new NewPublicTab();
		data.add(newgrouptab);data.add(newpublictab);
		adapter=new FragmentSlideAdapter(getSupportFragmentManager(),data);
		newsocialgroupviewpager.setAdapter(adapter);
		newsocialgroupviewpager.addOnPageChangeListener(newdynamicchangelistener);
	}
	private OnPageChangeListener newdynamicchangelistener= new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int index) {
			switch (index){
				case 0:newgrouptabbtn.setTextColor(Color.WHITE);
				newpublictabbtn.setTextColor(Color.BLACK);
					break;
				case 1:newgrouptabbtn.setTextColor(Color.BLACK);
				newpublictabbtn.setTextColor(Color.WHITE);
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
