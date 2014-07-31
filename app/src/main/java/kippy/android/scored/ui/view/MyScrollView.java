package kippy.android.scored.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by christianwhitehouse on 7/31/14.
 */
public class MyScrollView extends ScrollView {

	//================================================================================
	// Variables
	//================================================================================

	OnScrollListener mListener;

	//================================================================================
	// Constructor
	//================================================================================

	public MyScrollView(Context context) {
		super(context);
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	//================================================================================
	// Scrolling
	//================================================================================

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);

		if(mListener != null)
			mListener.onScrollChanged(this, l, t, oldl, oldt);
	}

	//================================================================================
	// Listener
	//================================================================================

	public void setOnScrollListener(OnScrollListener listener) {
		mListener = listener;
	}

	//================================================================================
	// Listener
	//================================================================================

	public interface OnScrollListener {
		public void onScrollChanged(MyScrollView scrollView, int l, int t, int oldl, int oldt);
	}
}
