package kippy.android.scored.stub.minigolf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
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
	protected int mRoundCount = -1;

	MinigolfFragment mMinigolfFragment;
	MinigolfScoreTotal vTotal;

	protected AvatarStub vAvatar;

	protected LinearLayout vScoresWrapper;
	protected MinigolfScoreEntry[] vScores;

	//================================================================================
	// Implementation
	//================================================================================

	@Override
	public int getStubID() {
		return R.id.minigolf_score_entries;
	}

	//================================================================================
	// Constructor
	//================================================================================

	public MinigolfPlayerScore(MinigolfFragment fragment, View parent, AvatarStub avatar, int playerIndex, int roundCount) {
		super(fragment.getMyActivity(), parent);
		mMinigolfFragment = fragment;

		mPlayerIndex = playerIndex;
		mRoundCount = roundCount;

		vAvatar = avatar;
		if(vAvatar == null)
			vAvatar = new AvatarStub(getContext(), parent);

		vTotal = new MinigolfScoreTotal(fragment, parent, mPlayerIndex);

		vScoresWrapper = (LinearLayout) vStub.findViewById(R.id.minigolf_score_entries);
		vScores = new MinigolfScoreEntry[mRoundCount];

		inflateNextRound(false);
	}

	//================================================================================
	// Layout
	//================================================================================

	public void inflateNextRound() {
		inflateNextRound(true);
	}

	private void inflateNextRound(boolean shouldAnimate) {
		mCurrentRound++;
		vScores[mCurrentRound] = MinigolfScoreEntry.inflate(mMinigolfFragment, vScoresWrapper, mCurrentRound, mPlayerIndex);
		vScoresWrapper.addView(vScores[mCurrentRound].getView(), mCurrentRound);

		if(shouldAnimate) {
			vScores[mCurrentRound].getView().setPivotY(0);
			vScores[mCurrentRound].getView().setScaleY(0f);
			vScores[mCurrentRound].getView().animate().scaleY(1f).setInterpolator(new DecelerateInterpolator());

			vTotal.getView().setTranslationY(-getContext().getResources().getDimensionPixelSize(R.dimen.minigolf_score_entry_size));
			vTotal.getView().animate().translationY(0f).setInterpolator(new DecelerateInterpolator());
		}
	}

	public int getTotalScore() {
		int total = 0;
		for(MinigolfScoreEntry scoreEntry : vScores) {
			if(scoreEntry != null)
				total += scoreEntry.getScore();
		}
		return total;
	}

	public void updateTotalScore(int total, boolean isWinning) {
		vTotal.layout(total, isWinning);
	}

	public void setScore(int round, int score) {
		if(vScores[round] != null)
			vScores[round].layout(score);
	}

	public boolean hasScore(int round) {
		return vScores[round] != null && vScores[round].hasScore();
	}

	public int getScore(int round) {
		if(vScores[round] != null)
			return vScores[round].getScore();
		else
			return -1;
	}

	public void highlightScore(int round) {
		if(vScores[round] != null)
			vScores[round].highlightScore();
	}

	public void unHighlightScore(int round) {
		if(vScores[round] != null)
			vScores[round].unHighlightScore();
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

	public static MinigolfPlayerScore inflate(MinigolfFragment fragment, ViewGroup parent, AvatarStub avatar, int playerIndex, int roundCount) {
		LayoutInflater layoutInflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View baseView = layoutInflater.inflate(R.layout.minigolf_score_sheet, parent, false);

		return new MinigolfPlayerScore(fragment, baseView, avatar, playerIndex, roundCount);
	}
}
