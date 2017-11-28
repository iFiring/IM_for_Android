package com.wedrips;

import com.wedrips.account.AccountFragment;
import com.wedrips.chat.ChatFragment;
import com.wedrips.dynamic.DynamicFragment;
import com.wedrips.plus.ExitAppActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener {
	public final static int PERSONAL_ID=0;
	private long finishtime=0;//程序退出确认
	private ChatFragment chatfrag;private DynamicFragment dynamicfrag;private AccountFragment accountfrag;
	private TextView chatText,dynamicText,accountText;
	private ImageView chatImg,dynamicImg,accountImg;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		ExitAppActivity.getInstance().addActivity(this);//加入Activity栈
		setDefaultMainTab(); //设置默认Tab
		findViewById(R.id.ChatBar).setOnClickListener(this);
		findViewById(R.id.DynamicBar).setOnClickListener(this);
		findViewById(R.id.AccountBar).setOnClickListener(this);
		chatText=(TextView) findViewById(R.id.chat);
		dynamicText=(TextView) findViewById(R.id.dynamic);
		accountText=(TextView) findViewById(R.id.account);
	}
	//设置默认主页
	public void setDefaultMainTab(){
		accountfrag=new AccountFragment();dynamicfrag=new DynamicFragment();chatfrag=new ChatFragment();
		getSupportFragmentManager().beginTransaction()
		.add(R.id.main_container, chatfrag)
		.add(R.id.main_container, accountfrag)
		.add(R.id.main_container, dynamicfrag)
		.commit();
	}
//	public void onBackPressed(){
//		startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));//返回桌面
//	}
	public void onBackPressed(){		//退出确认
		if (finishtime<=0){
			Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
			finishtime=System.currentTimeMillis();
		}else{
			long currentClickTime=System.currentTimeMillis();
			if(currentClickTime-finishtime<1000){
				finish();
			}else{
				Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
				finishtime=currentClickTime;
			}
		}
	}
	//主页选择按钮监听器
	public void onClick(View v) {
		chatImg=(ImageView) findViewById(R.id.ChatImg);
		dynamicImg=(ImageView) findViewById(R.id.DynamicImg);
		accountImg=(ImageView) findViewById(R.id.AccountImg);
		FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();//开启Fragment事务
		switch(v.getId()){
		case R.id.ChatBar:
			transaction.hide(dynamicfrag).hide(accountfrag).show(chatfrag).commit();
			chatImg.setImageResource(R.drawable.chat_btn_sl);
			dynamicImg.setImageResource(R.drawable.dynamic_btn);
			accountImg.setImageResource(R.drawable.account_btn);
			chatText.setTextColor(Color.parseColor("#0099FF"));
			dynamicText.setTextColor(Color.parseColor("#999999"));
			accountText.setTextColor(Color.parseColor("#999999"));
			break;
		case R.id.DynamicBar:
			transaction.hide(chatfrag).hide(accountfrag).show(dynamicfrag).commit();
			chatImg.setImageResource(R.drawable.chat_btn);
			dynamicImg.setImageResource(R.drawable.dynamic_btn_sl);
			accountImg.setImageResource(R.drawable.account_btn);
			chatText.setTextColor(Color.parseColor("#999999"));
			dynamicText.setTextColor(Color.parseColor("#0099FF"));//Color.rgb(255.255.255); Color.parseColor("#FFFFFF");亦可
			accountText.setTextColor(Color.parseColor("#999999"));
			break;
		case R.id.AccountBar:
			transaction.hide(dynamicfrag).hide(chatfrag).show(accountfrag).commit();
			chatImg.setImageResource(R.drawable.chat_btn);
			dynamicImg.setImageResource(R.drawable.dynamic_btn);
			accountImg.setImageResource(R.drawable.account_btn_sl);
			chatText.setTextColor(Color.parseColor("#999999"));
			dynamicText.setTextColor(Color.parseColor("#999999"));
			accountText.setTextColor(Color.parseColor("#0099FF"));
			break;
		}
	}
}