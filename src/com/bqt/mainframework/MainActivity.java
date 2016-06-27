package com.bqt.mainframework;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 使用android.app中的Fragment，继承自Activity
 * @author 白乾涛
 */
public class MainActivity extends Activity implements OnClickListener {
	/**
	 * 四个TextView控件
	 */
	private TextView[] mTabTVs = new TextView[4];
	/**
	 * 四个控件【未】按下时的图片id
	 */
	private int[] mTabTVIdsNormal;
	/**
	 * 四个控件按下时的图片id
	 */
	private int[] mTabTVIdsPress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initViews();
		setTabSelection(mTabTVs[0]);
	}

	private void initViews() {

		mTabTVs[0] = (TextView) findViewById(R.id.tv_tab_bottom_weixin);
		mTabTVs[1] = (TextView) findViewById(R.id.tv_tab_bottom_friend);
		mTabTVs[2] = (TextView) findViewById(R.id.tv_tab_bottom_contact);
		mTabTVs[3] = (TextView) findViewById(R.id.tv_tab_bottom_setting);

		mTabTVIdsNormal = new int[] { R.drawable.tab_weixin_normal, R.drawable.tab_find_frd_normal, R.drawable.tab_address_normal,
				R.drawable.tab_settings_normal };

		mTabTVIdsPress = new int[] { R.drawable.tab_weixin_pressed, R.drawable.tab_find_frd_pressed, R.drawable.tab_address_pressed,
				R.drawable.tab_settings_pressed };

		//给四个控件设置一个Tag，当我们点击某个控件时可以根据这个Tag来识别此控件，当然我们也可以根据v.getid()来识别，但在这里setTag还有其他妙用
		for (int i = 0; i < mTabTVs.length; i++) {
			mTabTVs[i].setOnClickListener(this);
			mTabTVs[i].setTag(i);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_tab_bottom_weixin:
		case R.id.tv_tab_bottom_friend:
		case R.id.tv_tab_bottom_contact:
		case R.id.tv_tab_bottom_setting:
			setTabSelection(v);
			break;

		default:
			break;
		}
	}

	private void setTabSelection(View v) {
		//清除掉所有的选中状态
		for (int i = 0; i < mTabTVs.length; i++) {
			mTabTVs[i].setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(mTabTVIdsNormal[i]), null, null);
			mTabTVs[i].setSelected(false);
		}

		// 改变控件的图片，这里的setSelected是为了演示通过selector来改变文字颜色
		int index = (Integer) v.getTag();
		((TextView) v).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(mTabTVIdsPress[index]), null, null);
		v.setSelected(true);
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		switch (index) {
		case 0:
			transaction.replace(R.id.id_content, new MainTabFragment("微信"));//每次都必须通过new的方式创建Fragment，不然可能出问题
			break;
		case 1:
			transaction.replace(R.id.id_content, new MainTabFragment("朋友"));
			break;
		case 2:
			transaction.replace(R.id.id_content, new MainTabFragment("通讯录"));
			break;
		case 3:
			transaction.replace(R.id.id_content, new MainTabFragment("设置"));
			break;
		}
		transaction.commit();
	}
}