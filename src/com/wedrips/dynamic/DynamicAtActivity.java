package com.wedrips.dynamic;

import com.wedrips.R;
import com.wedrips.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class DynamicAtActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dynamic_aboutme);
		findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
