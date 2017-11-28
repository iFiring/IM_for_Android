package com.wedrips;

import com.wedrips.login.LoginFragment;
import com.wedrips.plus.ExitAppActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

public class StartActivity extends FragmentActivity {
	
	private ImageView icon;private ImageView dream;
	private SharedPreferences loginpreferences;
	private Fragment loginFrag=new LoginFragment();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomepage);
		icon=(ImageView) findViewById(R.id.welcome_text);
		dream=(ImageView) findViewById(R.id.welcome_icon);
		ExitAppActivity.getInstance().removeActivity(this);
		
		// 判断登录状态
		loginpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
		Boolean isautologin = loginpreferences.getBoolean("isautologin", false);
		if (isautologin) {
			final Intent intent=new Intent(this, MainActivity.class);
			new Handler().postDelayed(new Runnable() { //延迟执行以显示主页
				public void run() {
					startActivity(intent);
					finish();
				}
			}, 2000);
		} else {
			icon.setVisibility(View.GONE);dream.setVisibility(View.GONE);
			getSupportFragmentManager().beginTransaction().add(R.id.container, loginFrag).commit();
		}
	}
}