package kippy.android.scored.activity;

import android.app.Activity;
import android.os.Bundle;

import kippy.android.scored.R;

/**
 * Created by christianwhitehouse on 6/20/14.
 */
public abstract class BaseActivity extends Activity {

	//================================================================================
	// Constants
	//================================================================================

	public static final int ANIMATION_STYLE_SLIDE_SIDEWAYS = 1;
	public static final int ANIMATION_STYLE_SLIDE_UP = 2;
	public static final int ANIMATION_STYLE_FADE_BACK = 3;

	//================================================================================
	// Static Variables
	//================================================================================

	private static int sVisibleActivityCount = 0;

	//================================================================================
	// Life Cycle Management
	//================================================================================

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		animateIn();
		setContentView(getLayoutID());
	}

	@Override
	public void onStart() {
		super.onStart();
		sVisibleActivityCount++;
	}

	@Override
	public void onStop() {
		super.onStop();
		sVisibleActivityCount--;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		animateOut();
	}

	@Override
	public void finish() {
		super.finish();
		animateOut();
	}

	//================================================================================
	// To Implement
	//================================================================================

	public abstract int getLayoutID();

	public int getAnimationStyle() {
		return 0;
	}

	//================================================================================
	// Animation Management
	//================================================================================

	public void animateIn() {
		overrideActivityAnimations(getAnimationStyle(), true);
	}

	public void animateOut() {
		overrideActivityAnimations(getAnimationStyle(), false);
	}

	public void overrideActivityAnimations(int animationStyle, boolean opening) {
		int enterAnimation = opening ? getInEnterAnimation(animationStyle) : getOutEnterAnimation(animationStyle);
		int exitAnimation = opening ? getInExitAnimation(animationStyle) : getOutExitAnimation(animationStyle);
		if(enterAnimation != 0 || exitAnimation != 0)
			overridePendingTransition(enterAnimation,exitAnimation);
	}

	private int getInEnterAnimation(int animationStyle) {
		switch(animationStyle) {
			case ANIMATION_STYLE_SLIDE_SIDEWAYS:
				return R.anim.activity_trans_slide_left_in;
			case ANIMATION_STYLE_SLIDE_UP:
				return R.anim.activity_trans_slide_up_in;
			case ANIMATION_STYLE_FADE_BACK:
				return R.anim.activity_trans_scaleup_in;
			default:
				return 0;
		}
	}

	private int getInExitAnimation(int animationStyle) {
		switch(animationStyle) {
			case ANIMATION_STYLE_SLIDE_SIDEWAYS:
				return R.anim.activity_trans_nudge_left;
			case ANIMATION_STYLE_SLIDE_UP:
				return R.anim.activity_trans_scaleback;
			case ANIMATION_STYLE_FADE_BACK:
				return R.anim.activity_trans_scaleup_out;
			default:
				return 0;
		}
	}

	private int getOutEnterAnimation(int animationStyle) {
		switch(animationStyle) {
			case ANIMATION_STYLE_SLIDE_SIDEWAYS:
				return R.anim.activity_trans_nudge_right;
			case ANIMATION_STYLE_SLIDE_UP:
				return R.anim.activity_trans_scaleup;
			case ANIMATION_STYLE_FADE_BACK:
				return R.anim.activity_trans_scaleback_in;
			default:
				return 0;
		}
	}

	private int getOutExitAnimation(int animationStyle) {
		switch(animationStyle) {
			case ANIMATION_STYLE_SLIDE_SIDEWAYS:
				return R.anim.activity_trans_slide_right_out;
			case ANIMATION_STYLE_SLIDE_UP:
				return R.anim.activity_trans_slide_down_out;
			case ANIMATION_STYLE_FADE_BACK:
				return R.anim.activity_trans_scaleback_out;
			default:
				return 0;
		}
	}

}
