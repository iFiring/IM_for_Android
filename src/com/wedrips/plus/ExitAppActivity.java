package com.wedrips.plus;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class ExitAppActivity extends Application {

	private List<Activity> activityList = new LinkedList<Activity>(); 
	private static ExitAppActivity instance;

	private ExitAppActivity(){
		
	}
	//单例模式中获取唯一的ExitApplication实例 
	public static ExitAppActivity getInstance(){
		if(null == instance)
	{
	instance = new ExitAppActivity();}
		return instance; 
	}
	//添加Activity到容器中
	public void addActivity(Activity activity){
		activityList.add(activity);
	}
	//删除栈顶Activity
	public void removeActivity(Activity activity){
		activityList.remove(activity);
	}
	//遍历所有Activity并finish
	public void exit(){
		for(Activity activity:activityList){
			activity.finish();}
		System.exit(0);
	}
}
