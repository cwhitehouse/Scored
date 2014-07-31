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
	protected int mCurrentRound = -1;
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

		inflateNextRound();
	}

	//================================================================================
	// Layout
	//================================================================================

	public void inflateNextRound() {
		mCurrentRound++;
		vScores[mCurrentRound] = MinigolfScoreEntry.inflate(getContext(), vScoresWrapper, mCurrentRound%2==0);
		vScoresWrapper.addView(vScores[mCurrentRound].getView());
	}

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

	public static MinigolfPlayerScore inflate(MinigolfFragment fragment, ViewGroup parent, AvatarStub avatar, int playerIndex, int holeCount) {
		LayoutInflater layoutInflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View baseView = layoutInflater.inflate(R.layout.minigolf_score_sheet, parent, false);

		return new MinigolfPlayerScore(fragment, baseView, avatar, playerIndex, holeCount);
	}
}
