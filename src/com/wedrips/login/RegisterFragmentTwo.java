package com.wedrips.login;

import com.wedrips.MainActivity;
import com.wedrips.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RegisterFragmentTwo extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceStete) {
		View root = inflater.inflate(R.layout.register_two, container, false);

		startActivity(new Intent(getActivity(), MainActivity.class));
		getActivity().finish();
		
		return root;
	}
}
