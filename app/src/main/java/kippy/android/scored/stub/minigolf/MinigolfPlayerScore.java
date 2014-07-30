package kippy.android.scored.stub.minigolf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import kippy.android.scored.R;
import kippy.android.scored.activity.BaseActivity;
import kippy.android.scored.stub.AvatarStub;
import kippy.android.scored.stub.MyStub;
import kippy.android.scored.util.MathUtils;

/**
 * Created by christianwhitehouse on 7/28/14.
 */
public class MinigolfPlayerScore extends MyStub {

	//================================================================================
	// Variables
	//================================================================================

	protected int mPlayerIndex;

	protected AvatarStub vAvatar;

	protected LinearLayout vScoresWrapper;
	protected MinigolfScoreEntry[] vScores;

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

	public MinigolfPlayerScore(BaseActivity activity, View parent, int playerIndex, int holeCount) {
		super(activity, parent);

		mPlayerIndex = playerIndex;

		vAvatar = new AvatarStub(activity, parent);

		vScoresWrapper = (LinearLayout) vStub.findViewById(R.id.minigolf_score_entries);
		vScores = new MinigolfScoreEntry[holeCount];
		for(int i=0 ; i<holeCount ; i++) {
			MinigolfScoreEntry scoreEntry = MinigolfScoreEntry.inflate(activity, vScoresWrapper, i%2 == 0);

			int score;
			if(MathUtils.randomInt(0,1) == 0)
				score = MathUtils.randomInt(2,3);
			else if(MathUtils.randomInt(1,35) == 35)
				score = 1;
			else
				score = MathUtils.randomInt(2,5);
			scoreEntry.layout(score);

			vScoresWrapper.addView(scoreEntry.getView());
			vScores[i] = scoreEntry;
		}
	}

	//================================================================================
	// Layout
	//================================================================================

	public void setScore(int hole, int score) {
		vScores[hole].layout(score);
	}

	//================================================================================
	// Convenience
	//================================================================================

	public AvatarStub getAvatar() {
		return vAvatar;
	}

	//================================================================================
	// Static Creation
	//================================================================================

	public static MinigolfPlayerScore inflate(BaseActivity activity, ViewGroup parent, int playerIndex, int holeCount) {
		LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View baseView = layoutInflater.inflate(R.layout.minigolf_score_sheet, parent, false);

		return new MinigolfPlayerScore(activity, baseView, playerIndex, holeCount);
	}
}
