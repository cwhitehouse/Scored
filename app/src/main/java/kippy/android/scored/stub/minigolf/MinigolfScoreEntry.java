package kippy.android.scored.stub.minigolf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import kippy.android.scored.R;
import kippy.android.scored.animation.BoomerangInterpolator;
import kippy.android.scored.animation.SpringInterpolator;
import kippy.android.scored.fragment.MinigolfFragment;
import kippy.android.scored.stub.MyStub;

/**
 * Created by christianwhitehouse on 7/30/14.
 */
public class MinigolfScoreEntry extends MyStub {

	//================================================================================
	// Variables
	//================================================================================

	MinigolfFragment mMinigolfFragment;

	View vHighlight;

	TextView vScore;
	View vScoreHighlight;

	TextView vAdd;

	int mCurrentScore = 0;

	int mRound = -1;
	int mPlayer = -1;

	//================================================================================
	// Stubby
	//================================================================================

	@Override
	public int getStubID() {
		return R.id.minigolf_score_entry;
	}

	//================================================================================
	// Constructor
	//================================================================================

	public MinigolfScoreEntry(MinigolfFragment minigolfFragment, View parent, int round, int player) {
		super(minigolfFragment.getMyActivity(), parent);
		mMinigolfFragment = minigolfFragment;

		mRound = round;
		mPlayer = player;

		vHighlight = vStub.findViewById(R.id.minigolf_score_entry_highlight);
		if(round%2 == 0) vHighlight.setVisibility(View.VISIBLE); else vHighlight.setVisibility(View.GONE);

		vScore = (TextView) vStub.findViewById(R.id.minigolf_score_entry_score);
		vScoreHighlight = vStub.findViewById(R.id.minigolf_score_entry_score_highlight);

		vAdd = (TextView) vStub.findViewById(R.id.minigolf_score_entry_add);
		vAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mMinigolfFragment.updateScoreForPlayer(mRound, mPlayer);
			}
		});
	}

	//================================================================================
	// Layout
	//================================================================================

	public void layout(int score) {
		if(score > 0) {
			if(score == 1) {
				vScoreHighlight.setScaleX(0.2f);
				vScoreHighlight.setScaleY(0.2f);
				vScoreHighlight.animate().alpha(1f).scaleX(1f).scaleY(1f).setInterpolator(new OvershootInterpolator(3f));
				vScore.animate().alpha(0f);
			} else {
				if(mCurrentScore <= 0) {
					vScore.setScaleY(0.2f);
					vScore.setScaleX(0.2f);
				}

				vScore.setText(String.valueOf(score));
				vScoreHighlight.animate().alpha(0f).scaleX(0f).scaleY(0f).setInterpolator(new DecelerateInterpolator());
				vScore.animate().alpha(1f).scaleX(1f).scaleY(1f).setInterpolator(new DecelerateInterpolator());
			}
			vAdd.animate().alpha(0f).scaleX(0f).scaleY(0f).setInterpolator(new DecelerateInterpolator());
		} else {
			vScore.animate().alpha(0f).scaleX(0f).scaleY(0f).setInterpolator(new DecelerateInterpolator());
			vScoreHighlight.animate().alpha(0f).scaleX(0f).scaleY(0f).setInterpolator(new DecelerateInterpolator());
			vAdd.animate().alpha(1f).scaleX(1f).scaleY(1f).setInterpolator(new DecelerateInterpolator());
		}
		mCurrentScore = score;
	}

	public void highlightScore() {
		vScore.setAlpha(1f);
		vScore.setTextColor(getContext().getResources().getColor(R.color.foregroundHighlight));
		vScore.animate().scaleY(1.35f).scaleX(1.35f).alpha(1f).setInterpolator(new BoomerangInterpolator(new SpringInterpolator(), new DecelerateInterpolator()));
	}

	public void unHighlightScore() {
		vScore.setAlpha(1f);
		vScore.setTextColor(getContext().getResources().getColor(R.color.foregroundPrimary));
	}

	public int getScore() {
		return mCurrentScore;
	}

	public boolean hasScore() {
		return mCurrentScore >= 1;
	}

	//================================================================================
	// Static Creation
	//================================================================================

	public static MinigolfScoreEntry inflate(MinigolfFragment minigolfFragment, ViewGroup parent, int round, int player) {
		LayoutInflater layoutInflater = (LayoutInflater) minigolfFragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View baseView = layoutInflater.inflate(R.layout.minigolf_score_entry, parent, false);

		return new MinigolfScoreEntry(minigolfFragment, baseView, round, player);
	}
}
