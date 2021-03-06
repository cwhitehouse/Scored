package kippy.android.scored.stub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import kippy.android.scored.R;
import kippy.android.scored.activity.BaseActivity;

/**
 * Created by christianwhitehouse on 7/29/14.
 */
public class AvatarStub extends MyStub {

	//================================================================================
	// Variables
	//================================================================================

	protected TextView vName;
	protected ImageView vImage;

	//================================================================================
	// Implementation
	//================================================================================

	@Override
	public int getStubID() {
		return R.id.my_avatar;
	}

	//================================================================================
	// Constructor
	//================================================================================

	public AvatarStub(BaseActivity activity, View parent) {
		super(activity, parent);

		vName = (TextView) vStub.findViewById(R.id.my_avatar_name);
	}

	//================================================================================
	// Layout
	//================================================================================

	public void layoutAvatar(String avatarURL, String username) {
		//TODO : Deal with the URL
		vName.setText(username);
	}

	//================================================================================
	// Static Creation
	//================================================================================

	public static AvatarStub inflate(BaseActivity activity, ViewGroup parent) {
		LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View baseView = layoutInflater.inflate(R.layout.my_avatar, parent, false);

		return new AvatarStub(activity, baseView);
	}

}
