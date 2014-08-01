package kippy.android.scored.stub.minigolf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import kippy.android.scored.R;
import kippy.android.scored.activity.BaseActivity;
import kippy.android.scored.animation.BoomerangInterpolator;
import kippy.android.scored.animation.SpringInterpolator;
import kippy.android.scored.stub.MyStub;

/**
 * Created by christianwhitehouse on 7/30/14.
 */
public class MinigolfScoreEntry extends MyStub {

	//================================================================================
	// Variables
	//================================================================================

	View vHighlight;

	TextView vScore;
	View vScoreHighlight;

	TextView vAdd;

	int mCurrentScore = -1;

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

	public MinigolfScoreEntry(BaseActivity activity, View parent, boolean shouldHighlight) {
		super(activity, parent);

		vHighlight = vStub.findViewById(R.id.minigolf_score_entry_highlight);
		if(shouldHighlight) vHighlight.setVisibility(View.VISIBLE); else vHighlight.setVisibility(View.GONE);

		vScore = (TextView) vStub.findViewById(R.id.minigolf_score_entry_score);
		vScoreHighlight = vStub.findViewById(R.id.minigolf_score_entry_score_highlight);

		vAdd = (TextView) vStub.findViewById(R.id.minigolf_score_entry_add);
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
				vScore.setText(String.valueOf(score));
				vScoreHighlight.animate().alpha(0f).setInterpolator(new DecelerateInterpolator());
				vScore.animate().alpha(1f);
			}
			vAdd.animate().alpha(0f);
		} else {
			vScore.animate().alpha(0f);
			vScoreHighlight.animate().alpha(0f);
			vAdd.animate().alpha(1f);
		}
		mCurrentScore = score;
	}

	public void highlightScore() {
		vScore.setAlpha(1f);
		vScore.setTextColor(getContext().getResources().getColor(R.color.foregroundHighlight));
		vScore.animate().scaleY(1.35f).scaleX(1.35f).alpha(1f).setInterpolator(new BoomerangInterpolator(new SpringInterpolator(), new DecelerateInterpolator()));
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

	public static MinigolfScoreEntry inflate(BaseActivity activity, ViewGroup parent, boolean shouldHighlight) {
		LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View baseView = layoutInflater.inflate(R.layout.minigolf_score_entry, parent, false);

		return new MinigolfScoreEntry(activity, baseView, shouldHighlight);
	}
}
