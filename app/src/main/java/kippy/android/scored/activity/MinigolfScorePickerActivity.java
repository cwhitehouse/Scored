package kippy.android.scored.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import kippy.android.scored.R;

/**
 * Created by christianwhitehouse on 8/20/14.
 */
public class MinigolfScorePickerActivity extends BaseActivity {

	public static final String EXTRA_SCORE = "EXTRA_SCORE";

	@Override
	public int getLayoutID() {
		return R.layout.dialog_minigolf_score_picker;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		View dialogTop = findViewById(R.id.dialog_top);
		dialogTop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		View dialogContent = findViewById(R.id.dialog_content);
		dialogContent.setClickable(true);

		View dialogBottom = findViewById(R.id.dialog_bottom);
		dialogBottom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public AnimationStyle getAnimationStyle() {
		return AnimationStyle.Dialog;
	}

	public void onScoreSelected(View v) {
		if(v instanceof TextView) {
			TextView textView = (TextView) v;
			String text = textView.getText().toString();

			try {
				int score = Integer.parseInt(text);
				if(score > 0) {
					Intent resultIntent = new Intent();
					resultIntent.putExtra(EXTRA_SCORE, score);
					setResult(Activity.RESULT_OK, resultIntent);
					finish();
				}
			} catch(NumberFormatException e){ e.printStackTrace(); }

		}
	}
}
