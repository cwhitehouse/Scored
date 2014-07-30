package kippy.android.scored.stub.minigolf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kippy.android.scored.R;
import kippy.android.scored.activity.BaseActivity;
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
	}

	//================================================================================
	// Layout
	//================================================================================

	public void layout(int score) {
		if(score > 0) {
			vScore.setText(String.valueOf(score));
			if(score == 1) {
				vScoreHighlight.animate().alpha(1f);
				vScore.setTextColor(getContext().getResources().getColor(R.color.foregroundAlternate));
			} else {
				vScoreHighlight.animate().alpha(0f);
				vScore.setTextColor(getContext().getResources().getColor(R.color.foregroundPrimary));
			}
		} else {
			vScore.setVisibility(View.INVISIBLE);
			vScoreHighlight.setAlpha(0f);
		}
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
