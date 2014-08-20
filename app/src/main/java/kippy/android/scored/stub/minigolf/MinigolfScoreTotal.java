package kippy.android.scored.stub.minigolf;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import kippy.android.scored.R;
import kippy.android.scored.animation.SpringInterpolator;
import kippy.android.scored.fragment.MinigolfFragment;
import kippy.android.scored.stub.MyStub;

/**
 * Created by christianwhitehouse on 7/30/14.
 */
public class MinigolfScoreTotal extends MyStub {

	//================================================================================
	// Variables
	//================================================================================

	MinigolfFragment mMinigolfFragment;

	TextView vScore;

	int mCurrentScore = 0;

	int mPlayer = -1;

	//================================================================================
	// Stubby
	//================================================================================

	@Override
	public int getStubID() {
		return R.id.minigolf_score_total;
	}

	//================================================================================
	// Constructor
	//================================================================================

	public MinigolfScoreTotal(MinigolfFragment minigolfFragment, View parent, int player) {
		super(minigolfFragment.getMyActivity(), parent);
		mMinigolfFragment = minigolfFragment;
		mPlayer = player;

		vScore = (TextView) vStub.findViewById(R.id.minigolf_score_total_score);
	}

	//================================================================================
	// Layout
	//================================================================================

	public void layout(int score, final boolean isWinning) {
		if(score > 0 && score != mCurrentScore) {
			mCurrentScore = score;
			if(vScore.getAlpha() > 0f) {
				vScore.animate().scaleX(0.1f).scaleY(0.1f).setInterpolator(new DecelerateInterpolator()).setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						super.onAnimationEnd(animation);

						vScore.setTextColor(getContext().getResources().getColor(isWinning ? R.color.foregroundHighlight : R.color.foregroundPrimary));
						vScore.setText(Integer.toString(mCurrentScore));
						vScore.animate().scaleX(1f).scaleY(1f).setInterpolator(new SpringInterpolator(0.8f)).setListener(null);
					}
				});
			} else {
				vScore.setScaleX(0.1f);
				vScore.setScaleY(0.1f);
				vScore.setTextColor(getContext().getResources().getColor(isWinning ? R.color.foregroundHighlight : R.color.foregroundPrimary));
				vScore.setText(Integer.toString(mCurrentScore));
				vScore.animate().alpha(1f).scaleX(1f).scaleY(1f).setInterpolator(new SpringInterpolator(0.8f)).setListener(null);
			}
		}
	}

	//================================================================================
	// Static Creation
	//================================================================================

	public static MinigolfScoreTotal inflate(MinigolfFragment minigolfFragment, ViewGroup parent, int round) {
		LayoutInflater layoutInflater = (LayoutInflater) minigolfFragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View baseView = layoutInflater.inflate(R.layout.minigolf_score_total, parent, false);

		return new MinigolfScoreTotal(minigolfFragment, baseView, round);
	}
}
