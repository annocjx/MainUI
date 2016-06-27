package com.bqt.mainframework;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * 只能用v4的Fragment
 * @author 白乾涛
 */
public class MainActivity3 extends SlidingFragmentActivity implements OnClickListener {
	private ViewPager mViewPager;
	private FragmentPagerAdapter mPagerAdapter;
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	private ImageView iv_menu_left;
	private ImageView iv_menu_right;

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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main3);
		initViews();
		initMenu();
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

		//点击弹出左右侧滑菜单
		iv_menu_left = (ImageView) findViewById(R.id.iv_menu_left);
		iv_menu_right = (ImageView) findViewById(R.id.iv_menu_right);
		iv_menu_left.setOnClickListener(this);
		iv_menu_right.setOnClickListener(this);
	}

	private void initMenu() {
		SlidingMenu menu = getSlidingMenu();
		//两侧通用设置
		menu.setMode(SlidingMenu.LEFT_RIGHT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);

		//左侧的菜单
		setBehindContentView(R.layout.menu_left);//左右侧不能使用同一个布局，并且replace掉的控件的ID不能相同！不知道为什么会有这种情况！
		Fragment leftMenuFragment = new MainTabFragment2("左侧菜单");
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_left, leftMenuFragment).commit();

		//右侧菜单设置
		menu.setSecondaryShadowDrawable(R.drawable.shadow);//根据资源文件ID来设置右边（二级）滑动菜单的阴影效果
		menu.setSecondaryMenu(R.layout.menu_right);//设置右边侧滑菜单
		Fragment rightMenuFragment = new MainTabFragment2("右侧菜单");
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_right, rightMenuFragment).commit();
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

		case R.id.iv_menu_left:
			getSlidingMenu().showMenu();
			break;

		case R.id.iv_menu_right:
			getSlidingMenu().showSecondaryMenu();
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