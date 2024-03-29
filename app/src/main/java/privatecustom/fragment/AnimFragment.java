package privatecustom.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.chenjh.eatwhat.R;

/**
 * 动画fragment
 *
 * @author dewyze
 *
 */
public class AnimFragment extends Fragment implements OnClickListener {

	private Activity mActivity;
	private View mWholeView;
	private ImageView mCancelImg;

	private OnFragmentDismissListener mListener;

	public interface OnFragmentDismissListener {
		public void onFragmentDismiss();
	}

	public AnimFragment(Fragment listener) {
		this.mListener = (OnFragmentDismissListener) listener;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_anim, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
		initEvents();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	private void initViews(View view) {
		mWholeView = view.findViewById(R.id.mark_layout);
		mCancelImg = (ImageView) view.findViewById(R.id.cancel_img);
	}

	private void initEvents() {
		mWholeView.setOnClickListener(this);
		mCancelImg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.mark_layout:
			case R.id.cancel_img:
				dismissFragment();
				break;

			default:
				break;
		}
	}

	private void dismissFragment() {
		if (null != mListener) {
			mListener.onFragmentDismiss();
		}
	}

}
