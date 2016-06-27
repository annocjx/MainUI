package com.bqt.mainframework;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * ʹ��android.app�е�Fragment���̳���Activity
 * @author ��Ǭ��
 */
public class MainActivity extends Activity implements OnClickListener {
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

		//���ĸ��ؼ�����һ��Tag�������ǵ��ĳ���ؼ�ʱ���Ը������Tag��ʶ��˿ؼ�����Ȼ����Ҳ���Ը���v.getid()��ʶ�𣬵�������setTag������������
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
		//��������е�ѡ��״̬
		for (int i = 0; i < mTabTVs.length; i++) {
			mTabTVs[i].setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(mTabTVIdsNormal[i]), null, null);
			mTabTVs[i].setSelected(false);
		}

		// �ı�ؼ���ͼƬ�������setSelected��Ϊ����ʾͨ��selector���ı�������ɫ
		int index = (Integer) v.getTag();
		((TextView) v).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(mTabTVIdsPress[index]), null, null);
		v.setSelected(true);
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		switch (index) {
		case 0:
			transaction.replace(R.id.id_content, new MainTabFragment("΢��"));//ÿ�ζ�����ͨ��new�ķ�ʽ����Fragment����Ȼ���ܳ�����
			break;
		case 1:
			transaction.replace(R.id.id_content, new MainTabFragment("����"));
			break;
		case 2:
			transaction.replace(R.id.id_content, new MainTabFragment("ͨѶ¼"));
			break;
		case 3:
			transaction.replace(R.id.id_content, new MainTabFragment("����"));
			break;
		}
		transaction.commit();
	}
}