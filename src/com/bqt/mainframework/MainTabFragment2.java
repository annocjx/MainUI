package com.bqt.mainframework;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 使用support.v4.app中的Fragment
 * @author 白乾涛
 */
public class MainTabFragment2 extends Fragment {
	private String name;

	public MainTabFragment2(String name) {
		this.name = name;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_tab_01, container, false);
		TextView tv = (TextView) view.findViewById(R.id.tv);
		tv.setText(new SimpleDateFormat("yyyy-MM-dd\nHH:mm:ss").format(new Date()) + "\n" + name);
		return view;
	}
}
