package com.wedrips.account;

import com.wedrips.R;
import com.wedrips.StartActivity;
import com.wedrips.plus.ExitAppActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountSetActivity extends Activity{
	private TextView exitapp;private TextView changeaccount;
	private SharedPreferences loginpreferences;private Editor logineditor;
	private ImageView backbtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_sets);
		ExitAppActivity.getInstance().addActivity(this);
		changeaccount=(TextView) findViewById(R.id.ChangeAccount);changeaccount.setOnClickListener(changeaccountlistener);
		exitapp=(TextView) findViewById(R.id.ExitApp);exitapp.setOnClickListener(exitapplistener);
		backbtn=(ImageView) findViewById(R.id.back_btn);
		backbtn.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {
			onBackPressed();}});
	}
	public void onBackPressed(){
		this.onDestroy();
		finish();
	}
	private OnClickListener changeaccountlistener=new OnClickListener(){
		@Override
		public void onClick(View v) {
			loginpreferences=getSharedPreferences("login",Context.MODE_PRIVATE);
			logineditor=loginpreferences.edit();
			logineditor.putBoolean("isautologin", false);
			logineditor.putString("pw", null);
			logineditor.commit();
			startActivity(new Intent(AccountSetActivity.this,StartActivity.class));//将栈顶的StartActivity删除
			ExitAppActivity.getInstance().exit();//结束栈顶之下的Activity
		}
	};
	private OnClickListener exitapplistener=new OnClickListener(){
		@Override
		public void onClick(View v) {
			ExitAppActivity.getInstance().exit();
		}
	};
}
	//下面是如何使用SlideSwitch来控制开关


//updateSwitch.setSlideListener(new SlideListener() {
//
//    @Override
//    public void open() {
//        // Do something ,,,
//    }
//
//    @Override
//    public void close() {
//        // Do something ,,,
//    }
//});


//public class MainActivity extends Activity implements SlideListener {
//
//	TextView txt;
//	SlideSwitch slide;
//	SlideSwitch slide2;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		slide = (SlideSwitch) findViewById(R.id.swit);
//		slide2 = (SlideSwitch) findViewById(R.id.swit2);
//
//		slide.setState(false);
//		txt = (TextView) findViewById(R.id.txt);
//		slide.setSlideListener(this);
//	}
//
//	@Override
//	public void open() {
//		// TODO Auto-generated method stub
//		txt.setText("first switch is opend, and set the second one is 'slideable'");
//		slide2.setSlideable(true);
//	}
//
//	@Override
//	public void close() {
//		// TODO Auto-generated method stub
//		txt.setText("first switch is closed,and set the second one is 'unslideable'");
//		slide2.setSlideable(false);
//	}
//}
