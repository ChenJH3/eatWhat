package privatecustom.fragment;

import com.example.chenjh.eatwhat.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import privatecustom.adapter.CateListAdapter;

public class CategoryFragment extends BaseFragment implements
		OnItemClickListener, OnClickListener, AnimFragment.OnFragmentDismissListener {

	private static final String TAG = "CategoryFragment";
	private Activity mActivity;
	private TextView mTitleTv;
	// 左侧分类ListView
	private ListView mCateListView;
	private CateListAdapter mCateListAdapter;
	private String[] mCategories;
	private ImageView mCateIndicatorImg;
	// 用于记录动画开始的位置
	private int mFromY = 0;
	private ImageButton mImageBtn;

	public static CategoryFragment newInstance() {
		CategoryFragment categoryFragment = new CategoryFragment();

		return categoryFragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_category, container,
				false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mCategories = mActivity.getResources()
				.getStringArray(R.array.cate_list);

		mCateListAdapter = new CateListAdapter(mActivity, mCategories);
		mCateListView.setAdapter(mCateListAdapter);

		// 用于计算mCateIndicatorImg的高度
		int itemHeight = calculateListViewItemHeight();
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		mCateIndicatorImg.measure(w, h);

		// 第一次进来设置indicator的位置
		doAnimation(itemHeight / 2 - mCateIndicatorImg.getMeasuredHeight());
	}

	private void initViews(View view) {

		mTitleTv = (TextView) view.findViewById(R.id.title_tv);
		mTitleTv.setText(R.string.tab_message);

		mCateListView = (ListView) view.findViewById(R.id.cate_listview);
		mCateListView.setOnItemClickListener(this);
		mCateIndicatorImg = (ImageView) view
				.findViewById(R.id.cate_indicator_img);

		mImageBtn = (ImageButton) view.findViewById(R.id.image_btn);
		mImageBtn.setOnClickListener(this);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		if (null != mCateListAdapter) {
			mCateListAdapter.setSelectedPos(position);
		}
		int toY = view.getTop() + view.getHeight() / 2;
		doAnimation(toY);
	}

	private void doAnimation(int toY) {
		int cateIndicatorY = mCateIndicatorImg.getTop()
				+ mCateIndicatorImg.getMeasuredHeight() / 2;
		TranslateAnimation animation = new TranslateAnimation(0, 0, mFromY
				- cateIndicatorY, toY - cateIndicatorY);
		animation.setInterpolator(new AccelerateDecelerateInterpolator());
		animation.setFillAfter(true);
		animation.setDuration(400);
		mCateIndicatorImg.startAnimation(animation);
		mFromY = toY;
	}

	private int calculateListViewItemHeight() {
		ListAdapter listAdapter = mCateListView.getAdapter();
		if (listAdapter == null) {
			return 0;
		}

		int totalHeight = 0;
		int count = listAdapter.getCount();
		for (int i = 0; i < count; i++) {
			View listItem = listAdapter.getView(i, null, mCateListView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		return totalHeight / count;
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.image_btn:
				showAnimFragment();
				break;

			default:
				break;
		}
	}

	/**
	 * 显示侧边的动画fragment
	 */
	private void showAnimFragment() {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setCustomAnimations(R.anim.left_in, R.anim.right_out);
//		ft.setCustomAnimations(R.anim.bottom_in, R.anim.bottom_out);
		Fragment prev = getFragmentManager().findFragmentByTag("anim_fragment");
		if (prev != null) {
			ft.remove(prev);
		}
		AnimFragment animFragment = new AnimFragment(this);
		ft.addToBackStack(null);
		ft.add(R.id.anim_fragment_layout, animFragment, "anim_fragment")
				.commitAllowingStateLoss();
	}

	/**
	 * 隐藏侧边动画fragment
	 */
	private void dismissAnimFragment() {
		getFragmentManager().popBackStack();
	}

	@Override
	public void onFragmentDismiss() {
		dismissAnimFragment();
	}

}
