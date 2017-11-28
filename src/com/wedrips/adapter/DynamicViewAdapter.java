package com.wedrips.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class DynamicViewAdapter extends FragmentPagerAdapter {

	private List<Fragment> fraggroup;
	private int windowwidth;
	public DynamicViewAdapter(FragmentManager fm,List<Fragment> viewgroup,int windowwidth) {
		super(fm);
		this.fraggroup=viewgroup;
		this.windowwidth=windowwidth;
	}
	@Override
	public int getCount() {
		return fraggroup == null ? 0 : fraggroup.size();
	}
	@Override
	public Fragment getItem(int position) {
		return fraggroup.get(position);
	}
//	@Override
//	public boolean isViewFromObject(View arg0, Object arg1) {
//		return arg0==arg1;
//	}
//	@Override  
//  public void destroyItem(ViewGroup container, int position, Object object)   {
//      container.removeView(fraggroup.get(position));//删除页卡  
//  }  
//	@Override
//	public void setPrimaryItem(View container, int position, Object object){
//      
//  }
//  @Override  
//  public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡  
//       return fraggroup.get(position);  
//  }
}
