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
 * ֻ����v4��Fragment
 * @author ��Ǭ��
 */
public class MainActivity3 extends SlidingFragmentActivity implements OnClickListener {
	private ViewPager mViewPager;
	private FragmentPagerAdapter mPagerAdapter;
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	private ImageView iv_menu_left;
	private ImageView iv_menu_right;

	/**
	 * �ĸ�TextView�ؼ�
	 */
	private TextView[] mTabTVs = new TextView[4];
	/**
	 * �ĸ��ؼ���δ������ʱ��ͼƬid
	 */
	private int[] mTabTVIdsNormal;
	/**
	 * �ĸ��ؼ�����ʱ��ͼƬid
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

		//���ĸ��ؼ�����һ��Tag�������ǵ��ĳ���ؼ�ʱ���Ը������Tag��ʶ��˿ؼ�����Ȼ����Ҳ���Ը���v.getid()��ʶ�𣬵�������setTag������������
		for (int i = 0; i < mTabTVs.length; i++) {
			mTabTVs[i].setOnClickListener(this);
			mTabTVs[i].setTag(i);
		}

		//����������Ҳ໬�˵�
		iv_menu_left = (ImageView) findViewById(R.id.iv_menu_left);
		iv_menu_right = (ImageView) findViewById(R.id.iv_menu_right);
		iv_menu_left.setOnClickListener(this);
		iv_menu_right.setOnClickListener(this);
	}

	private void initMenu() {
		SlidingMenu menu = getSlidingMenu();
		//����ͨ������
		menu.setMode(SlidingMenu.LEFT_RIGHT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);

		//���Ĳ˵�
		setBehindContentView(R.layout.menu_left);//���Ҳ಻��ʹ��ͬһ�����֣�����replace���Ŀؼ���ID������ͬ����֪��Ϊʲô�������������
		Fragment leftMenuFragment = new MainTabFragment2("���˵�");
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_left, leftMenuFragment).commit();

		//�Ҳ�˵�����
		menu.setSecondaryShadowDrawable(R.drawable.shadow);//������Դ�ļ�ID�������ұߣ������������˵�����ӰЧ��
		menu.setSecondaryMenu(R.layout.menu_right);//�����ұ߲໬�˵�
		Fragment rightMenuFragment = new MainTabFragment2("�Ҳ�˵�");
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_right, rightMenuFragment).commit();
	}

	private void initViewPage() {
		mFragments.add(new MainTabFragment2("΢��"));
		mFragments.add(new MainTabFragment2("����"));
		mFragments.add(new MainTabFragment2("ͨѶ¼"));
		mFragments.add(new MainTabFragment2("����"));

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
	 *������tab��״̬�ı�ʱ������ViewPagerѡ����
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
	 *��ViewPagerѡ����ı�ʱ����������tab��״̬
	 */
	private void setTabSelection(View v) {
		//��������е�ѡ��״̬
		for (int i = 0; i < mTabTVs.length; i++) {
			mTabTVs[i].setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(mTabTVIdsNormal[i]), null, null);
			mTabTVs[i].setSelected(false);
		}

		// �ı�ؼ���ͼƬ�������setSelected��Ϊ����ʾͨ��selector���ı�������ɫ
		int index = (Integer) v.getTag();
		((TextView) v).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(mTabTVIdsPress[index]), null, null);
		v.setSelected(true);
	}
}