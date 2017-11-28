package com.wedrips.login;

import com.wedrips.MainActivity;
import com.wedrips.R;
import com.wedrips.plus.SoftKeyBoardListener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment implements OnClickListener{
	private SharedPreferences loginpreferences;
	private Editor logineditor;
	private EditText loginid,loginpw;
	private int keyboardheight;//输入法高度
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceStete){
		View root= inflater.inflate(R.layout.login,container,false);
		loginid=(EditText)root.findViewById(R.id.login_id);
		loginpw=(EditText)root.findViewById(R.id.login_pw);
		loginpreferences=getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);//请求检索Shared Preferences类
		logineditor=loginpreferences.edit();
		root.findViewById(R.id.login_btn).setOnClickListener(this);
		root.findViewById(R.id.findpw_btn).setOnClickListener(this);
		root.findViewById(R.id.register_btn).setOnClickListener(this);
		return root;
	}
	public LoginFragment(){
		 
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.login_btn:
			//检查用户输入状态
			String checkresult=checklogin();
			if(checkresult!=null){
				Toast.makeText(getActivity(), checkresult,Toast.LENGTH_LONG).show();
			}else{
			//联网核实登录信息
			
			//登录信息核实成功，向SharedPreferencce写入登录信息
			logineditor.putString("id", loginid.getText().toString());
			logineditor.putString("pw", loginpw.getText().toString());
			logineditor.putBoolean("isautologin", true);
			logineditor.commit();
			startActivity(new Intent(getActivity(),MainActivity.class));
			getActivity().finish();
			}
			break;
		case R.id.findpw_btn:
			getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container,new FindPwFragment()).commit();
			break;
		case R.id.register_btn:
			getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container,new RegisterFragmentOne()).commit();
			break;
		}
	}
	private String checklogin(){
		if(loginid.getText().toString()==null||loginid.getText().toString().equals("")){
			return"号码不能为空";
		}
		if(loginid.getText().toString().trim().length()!=11){
			return"号码为11位数字";
		}
		if(loginpw.getText().toString()==null||loginpw.getText().toString().equals("")){
			return"密码不能为空";
		}
		if(loginpw.getText().toString().trim().length()!=6){
			return"密码为6位数字";
		}
		return null;
	}
}
