package kippy.android.scored.stub;

import android.view.View;

import kippy.android.scored.activity.BaseActivity;

/**
 * Created by christianwhitehouse on 5/8/14.
 */
public abstract class MyStub {

	//================================================================================
	// Variables
	//================================================================================

	private BaseActivity mActivity;

	public View vParent;
	public View vStub;

	//================================================================================
	// Constructor
	//================================================================================

	public MyStub(BaseActivity activity, View parent) {
		mActivity = activity;

		vParent = parent;
		if(vParent != null)
			vStub = parent.findViewById(getStubID());
	}

	//================================================================================
	// Abstract Methods
	//================================================================================

	public abstract int getStubID();

	//================================================================================
	// Convenience Methods
	//================================================================================

	public BaseActivity getContext() {
		return mActivity;
	}

	public View getView() {
		return vStub;
	}

	public void setVisibility(int visibility) {
		if(vStub != null) vStub.setVisibility(visibility);
	}

}
