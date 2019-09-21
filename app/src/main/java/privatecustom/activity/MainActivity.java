package privatecustom.activity;

import com.example.chenjh.eatwhat.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import privatecustom.fragment.CategoryFragment;
import privatecustom.fragment.CollectFragment;
import privatecustom.fragment.HomeFragment;
import privatecustom.fragment.SettingFragment;
import privatecustom.utils.ConstantValues;
import privatecustom.view.MyTabWidget;

/**
 * 导航页
 *
 * @author dewyze
 *
 */
public class MainActivity extends FragmentActivity implements
		MyTabWidget.OnTabSelectedListener {

	private static final String TAG = "MainActivity";
	private MyTabWidget mTabWidget;
	private HomeFragment mHomeFragment;
	private CategoryFragment mCategoryFragment;
	private CollectFragment mCollectFragment;
	private SettingFragment mSettingFragment;
	private int mIndex = ConstantValues.HOME_FRAGMENT_INDEX;
	private FragmentManager mFragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
		initEvents();
	}

	private void init() {
		mFragmentManager = getSupportFragmentManager();
		mTabWidget = (MyTabWidget) findViewById(R.id.tab_widget);
	}

	private void initEvents() {
		mTabWidget.setOnTabSelectedListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		onTabSelected(mIndex);
		mTabWidget.setTabsDisplay(this, mIndex);
		mTabWidget.setIndicateDisplay(this, 3, true);
	}

	@Override
	public void onTabSelected(int index) {
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		hideFragments(transaction);
		switch (index) {
			case ConstantValues.HOME_FRAGMENT_INDEX:
				if (null == mHomeFragment) {
					mHomeFragment = new HomeFragment();
					transaction.add(R.id.center_layout, mHomeFragment);
				} else {
					transaction.show(mHomeFragment);
				}
				break;
			case ConstantValues.CATEGORY_FRAGMENT_INDEX:
				if (null == mCategoryFragment) {
					mCategoryFragment = new CategoryFragment();
					transaction.add(R.id.center_layout, mCategoryFragment);
				} else {
					transaction.show(mCategoryFragment);
				}
				break;
			case ConstantValues.COLLECT_FRAGMENT_INDEX:
				if (null == mCollectFragment) {
					mCollectFragment = new CollectFragment();
					transaction.add(R.id.center_layout, mCollectFragment);
				} else {
					transaction.show(mCollectFragment);
				}
				break;
			case ConstantValues.SETTING_FRAGMENT_INDEX:
				if (null == mSettingFragment) {
					mSettingFragment = new SettingFragment();
					transaction.add(R.id.center_layout, mSettingFragment);
				} else {
					transaction.show(mSettingFragment);
				}
				break;

			default:
				break;
		}
		mIndex = index;
		transaction.commitAllowingStateLoss();
	}

	private void hideFragments(FragmentTransaction transaction) {
		if (null != mHomeFragment) {
			transaction.hide(mHomeFragment);
		}
		if (null != mCategoryFragment) {
			transaction.hide(mCategoryFragment);
		}
		if (null != mCollectFragment) {
			transaction.hide(mCollectFragment);
		}
		if (null != mSettingFragment) {
			transaction.hide(mSettingFragment);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// 自己记录fragment的位置,防止activity被系统回收时，fragment错乱的问题
		// super.onSaveInstanceState(outState);
		outState.putInt("index", mIndex);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// super.onRestoreInstanceState(savedInstanceState);
		mIndex = savedInstanceState.getInt("index");
	}

}
