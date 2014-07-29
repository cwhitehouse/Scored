package kippy.android.scored.stub.minigolf;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import kippy.android.scored.R;
import kippy.android.scored.activity.BaseActivity;
import kippy.android.scored.stub.AvatarStub;
import kippy.android.scored.stub.MyStub;

/**
 * Created by christianwhitehouse on 7/28/14.
 */
public class MinigolfUserScores extends MyStub {

	//================================================================================
	// Variables
	//================================================================================

	protected LinearLayout vWrapper;
	protected AvatarStub vAvatar;

	protected LinearLayout vScoresWrapper;
	protected TextView[] vScores;

	//================================================================================
	// Implementation
	//================================================================================

	@Override
	public int getStubID() {
		return R.id.minigolf_score_entry;
	}

	//================================================================================
	// Constructor
	//================================================================================

	public MinigolfUserScores(BaseActivity activity, View parent) {
		super(activity, parent);
	}

	//================================================================================
	// Layout
	//================================================================================

	public void setScore(int hole, int score) {
		vScores[hole].setText(String.valueOf(score));
	}

	//================================================================================
	// Convenience
	//================================================================================

	public AvatarStub getAvatar() {
		return vAvatar;
	}
}
