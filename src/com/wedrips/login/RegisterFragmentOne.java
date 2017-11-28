package com.wedrips.login;

import com.wedrips.MainActivity;
import com.wedrips.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterFragmentOne extends Fragment {
	private EditText registernickname, registername, registerage, registersex;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceStete) {
		View root = inflater.inflate(R.layout.register_one, container, false);
		registernickname = (EditText) root.findViewById(R.id.register_nickname);
		registername = (EditText) root.findViewById(R.id.register_name);
		registerage = (EditText) root.findViewById(R.id.register_age);
		registersex = (EditText) root.findViewById(R.id.register_sex);
		root.findViewById(R.id.registertwo_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String checkresult = checkregistertwo();
				if (checkresult != null) {
					Toast.makeText(getActivity(), checkresult, Toast.LENGTH_LONG).show();
				} else {
					startActivity(new Intent(getActivity(), MainActivity.class));
					getActivity().finish();
					//getFragmentManager().beginTransaction().replace(R.id.container, new RegisterFragmentTwo()).commit();
				}
			}
		});
		return root;
	}

	private String checkregistertwo() {
		if (registernickname.getText().toString() == null || registernickname.getText().toString().equals("")) {
			return "昵称不能为空";
		}
		if (registername.getText().toString() == null || registername.getText().toString().equals("")) {
			return "姓名不能为空";
		}
		if (registerage.getText().toString() == null || registerage.getText().toString().equals("")) {
			return "年龄不能为空";
		}
		if (registersex.getText().toString() == null || registersex.getText().toString().equals("")) {
			return "性别不能为空";
		}
		return null;
	}
}
