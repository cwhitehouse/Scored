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
public class MinigolfRoundEntry extends MyStub {

	//================================================================================
	// Variables
	//================================================================================

	View vHighlight;
	TextView vNumber;

	//================================================================================
	// Constructor
	//================================================================================

	public MinigolfRoundEntry(BaseActivity activity, View parent, boolean shouldHighlight) {
		super(activity, parent);

		vHighlight = vStub.findViewById(R.id.minigolf_round_entry_highlight);
		if(shouldHighlight) vHighlight.setVisibility(View.VISIBLE); else vHighlight.setVisibility(View.GONE);

		vNumber = (TextView) vStub.findViewById(R.id.minigolf_round_entry_number);
	}

	//================================================================================
	// Stubbage
	//================================================================================

	@Override
	public int getStubID() {
		return R.id.minigolf_rond_entry;
	}

	//================================================================================
	// Layout
	//================================================================================

	public void layout(int number) {
		if(number > 0) {
			vNumber.setVisibility(View.VISIBLE);
			vNumber.setText(String.valueOf(number));
		} else
			vNumber.setVisibility(View.GONE);
	}

	public void layout(String text) {
		vNumber.setText(text);
		vStub.setBackgroundResource(R.color.backgroundPrimaryDark);
	}

	//================================================================================
	// Static Creation
	//================================================================================

	public static MinigolfRoundEntry inflate(BaseActivity activity, ViewGroup parent, boolean shouldHighlight) {
		LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View baseView = layoutInflater.inflate(R.layout.minigolf_round_entry, parent, false);

		return new MinigolfRoundEntry(activity, baseView, shouldHighlight);
	}

}
