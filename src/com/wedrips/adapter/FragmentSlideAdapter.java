package com.wedrips.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentSlideAdapter extends FragmentPagerAdapter{
	private List<Fragment> data;		
	public FragmentSlideAdapter(FragmentManager fm, List<Fragment> data) {
		super(fm);
		this.data = data;
	}

	public FragmentSlideAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return data.get(position);
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}
}
