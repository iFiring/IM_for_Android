package com.wedrips.chat;

import com.wedrips.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ContactsList extends Activity {
	private ImageView newplusbtn;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_layout);
		newplusbtn=(ImageView) findViewById(R.id.contacts_plus);
		newplusbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				startActivity(new Intent(ContactsList.this,NewSocialGroup.class));
				Toast.makeText(ContactsList.this, "你点击了添加按钮", Toast.LENGTH_SHORT).show();
			}
		});
		findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
	public void onBackPressed(){
		finish();
	}
}
