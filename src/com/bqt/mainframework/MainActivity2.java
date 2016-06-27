package com.bqt.mainframework;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 使用support.v4.app中的Fragment，继承自FragmentActivity
 * 没办法，使用FragmentPagerAdapter就不可以用app的Fragment，只能用v4的Fragment
 * 有个问题：viewpage响应的条目会因为自己的缓存机制导致不更新页面
 * @author 白乾涛
 */
public class MainActivity2 extends FragmentActivity implements OnClickListener {
	private ViewPager mViewPager;
	private FragmentPagerAdapter mPagerAdapter;
	private List<Fragment> mFragments = new ArrayList<Fragment>();

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
		setContentView(R.layout.main2);
		initViews();
		initViewPage();
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

	private void initViewPage() {
		mFragments.add(new MainTabFragment2("微信"));
		mFragments.add(new MainTabFragment2("朋友"));
		mFragments.add(new MainTabFragment2("通讯录"));
		mFragments.add(new MainTabFragment2("设置"));

		mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return mFragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return mFragments.get(arg0);
			}
		};

		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				setTabSelection(mTabTVs[position]);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
		});
	}

	@Override
	/**
	 *当下面tab的状态改变时，更改ViewPager选中项
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_tab_bottom_weixin:
		case R.id.tv_tab_bottom_friend:
		case R.id.tv_tab_bottom_contact:
		case R.id.tv_tab_bottom_setting:
			setTabSelection(v);
			mViewPager.setCurrentItem((Integer) v.getTag());
			break;

		default:
			break;
		}
	}

	/**
	 *当ViewPager选中项改变时，更改下面tab的状态
	 */
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
	}
}