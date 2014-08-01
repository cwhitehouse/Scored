package kippy.android.scored.stub.minigolf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import kippy.android.scored.R;
import kippy.android.scored.fragment.MinigolfFragment;
import kippy.android.scored.stub.AvatarStub;
import kippy.android.scored.stub.MyStub;

/**
 * Created by christianwhitehouse on 7/28/14.
 */
public class MinigolfPlayerScore extends MyStub {

	//================================================================================
	// Variables
	//================================================================================

	protected int mPlayerIndex = -1;
	protected int mCurrentHole = -1;
	protected int mHoleCount = -1;

	MinigolfFragment mFragment;

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

	public MinigolfPlayerScore(MinigolfFragment fragment, View parent, AvatarStub avatar, int playerIndex, int holeCount) {
		super(fragment.getMyActivity(), parent);
		mFragment = fragment;

		mPlayerIndex = playerIndex;
		mHoleCount = holeCount;

		vAvatar = avatar;
		if(vAvatar == null)
			vAvatar = new AvatarStub(getContext(), parent);

		vScoresWrapper = (LinearLayout) vStub.findViewById(R.id.minigolf_score_entries);
		vScores = new MinigolfScoreEntry[mHoleCount];

		inflateNextHole();
	}

	//================================================================================
	// Layout
	//================================================================================

	public void inflateNextHole() {
		mCurrentHole++;
		vScores[mCurrentHole] = MinigolfScoreEntry.inflate(getContext(), vScoresWrapper, mCurrentHole %2==0);
		vScoresWrapper.addView(vScores[mCurrentHole].getView());
	}

	public void setScore(int hole, int score) {
		vScores[hole].layout(score);
	}

	public boolean hasScoreForRound(int hole) {
		return vScores[hole] != null && vScores[hole].hasScore();
	}

	public int getScoreForRound(int hole) {
		if(vScores[hole] != null)
			return vScores[hole].getScore();
		else
			return -1;
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

	public static MinigolfPlayerScore inflate(MinigolfFragment fragment, ViewGroup parent, AvatarStub avatar, int playerIndex, int holeCount) {
		LayoutInflater layoutInflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View baseView = layoutInflater.inflate(R.layout.minigolf_score_sheet, parent, false);

		return new MinigolfPlayerScore(fragment, baseView, avatar, playerIndex, holeCount);
	}
}
