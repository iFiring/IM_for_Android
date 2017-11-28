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

public class FindPwFragment extends Fragment{
	private EditText findpwid,findpwpw;
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceStete){
		View root=inflater.inflate(R.layout.findpassword,container,false);
		findpwid=(EditText)root.findViewById(R.id.findpw_id);
		findpwpw=(EditText)root.findViewById(R.id.findpw_pw);
		root.findViewById(R.id.findpw_confirm_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String checkresult=checkfindpw();
				if(checkresult!=null){
					Toast.makeText(getActivity(), checkresult,Toast.LENGTH_LONG).show();
				}else{
				startActivity(new Intent(getActivity(),MainActivity.class));
				getActivity().finish();}
			}
		});
		root.findViewById(R.id.findpw_back_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getFragmentManager().popBackStack();
			}
		});
		return root;
	}
	private String checkfindpw(){
		if(findpwid.getText().toString()==null||findpwid.getText().toString().equals("")){
			return"号码不能为空";
		}
		if(findpwid.getText().toString().trim().length()!=11){
			return"号码为11位数字";
		}
		if(findpwpw.getText().toString()==null||findpwpw.getText().toString().equals("")){
			return"密码不能为空";
		}
		if(findpwpw.getText().toString().trim().length()!=6){
			return"密码为6位数字";
		}
		return null;
	}
}
