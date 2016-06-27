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
 * ʹ��support.v4.app�е�Fragment���̳���FragmentActivity
 * û�취��ʹ��FragmentPagerAdapter�Ͳ�������app��Fragment��ֻ����v4��Fragment
 * �и����⣺viewpage��Ӧ����Ŀ����Ϊ�Լ��Ļ�����Ƶ��²�����ҳ��
 * @author ��Ǭ��
 */
public class MainActivity2 extends FragmentActivity implements OnClickListener {
	private ViewPager mViewPager;
	private FragmentPagerAdapter mPagerAdapter;
	private List<Fragment> mFragments = new ArrayList<Fragment>();

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

		//���ĸ��ؼ�����һ��Tag�������ǵ��ĳ���ؼ�ʱ���Ը������Tag��ʶ��˿ؼ�����Ȼ����Ҳ���Ը���v.getid()��ʶ�𣬵�������setTag������������
		for (int i = 0; i < mTabTVs.length; i++) {
			mTabTVs[i].setOnClickListener(this);
			mTabTVs[i].setTag(i);
		}
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