package com.wedrips.dynamic;

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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NewDynamicFragAct extends FragmentActivity {
	private ViewPager newdynamicviewpager; private FragmentSlideAdapter adapter;
	private TextView newdynamictabbtn,newactivitytabbtn;
	private ImageView backbtn,confirmbtn;
	private NewPhoto newdynamictab;private NewActivity newactivitytab;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_dynamic_layout);
		newdynamictabbtn=(TextView) findViewById(R.id.newdynamic_btn);
		newactivitytabbtn=(TextView) findViewById(R.id.newactivity_btn);
		backbtn=(ImageView) findViewById(R.id.back_btn);
		confirmbtn=(ImageView) findViewById(R.id.confirm_btn);
		viewpagerfun();
		newdynamicviewpager.setCurrentItem(0);
		newdynamictabbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				newdynamicviewpager.setCurrentItem(0); 
			}
		});
		newactivitytabbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				newdynamicviewpager.setCurrentItem(1); 
			}
		});
	}
	public void viewpagerfun(){
		newdynamicviewpager=(ViewPager) findViewById(R.id.new_dynamic_container);
		newdynamicviewpager.setOffscreenPageLimit(2);
		List<Fragment> data=new ArrayList<Fragment>();
		newdynamictab=new NewPhoto();
		newactivitytab=new NewActivity();
		data.add(newdynamictab);data.add(newactivitytab);
		adapter=new FragmentSlideAdapter(getSupportFragmentManager(),data);
		newdynamicviewpager.setAdapter(adapter);
		newdynamicviewpager.addOnPageChangeListener(newdynamicchangelistener);
	}
	private OnPageChangeListener newdynamicchangelistener= new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int index) {
			switch (index){
				case 0:newdynamictabbtn.setTextColor(Color.WHITE);
				newactivitytabbtn.setTextColor(Color.BLACK);
					break;
				case 1:newdynamictabbtn.setTextColor(Color.BLACK);
				newactivitytabbtn.setTextColor(Color.WHITE);
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
