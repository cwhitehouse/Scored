package kippy.android.scored.stub;

import android.view.View;
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

}
