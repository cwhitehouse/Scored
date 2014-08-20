package kippy.android.scored.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import kippy.android.scored.R;

/**
 * Created by christianwhitehouse on 6/20/14.
 */
public abstract class BaseActivity extends Activity {

	//================================================================================
	// Constants
	//================================================================================

	protected static enum AnimationStyle {
		None,
		SlideSideways,
		SlideUp,
		FadeBack,
		Dialog,
	}

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
		getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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

	public AnimationStyle getAnimationStyle() {
		return AnimationStyle.None;
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

	public void overrideActivityAnimations(AnimationStyle animationStyle, boolean opening) {
		int enterAnimation = opening ? getInEnterAnimation(animationStyle) : getOutEnterAnimation(animationStyle);
		int exitAnimation = opening ? getInExitAnimation(animationStyle) : getOutExitAnimation(animationStyle);
		if(enterAnimation != 0 || exitAnimation != 0)
			overridePendingTransition(enterAnimation,exitAnimation);
	}

	private int getInEnterAnimation(AnimationStyle animationStyle) {
		switch(animationStyle) {
			case SlideSideways:
				return R.anim.activity_trans_slide_left_in;
			case SlideUp:
				return R.anim.activity_trans_slide_up_in;
			case FadeBack:
			case Dialog:
				return R.anim.activity_trans_scaleback_in;
			default:
				return 0;
		}
	}

	private int getInExitAnimation(AnimationStyle animationStyle) {
		switch(animationStyle) {
			case SlideSideways:
				return R.anim.activity_trans_nudge_left;
			case SlideUp:
				return R.anim.activity_trans_scaleback;
			case FadeBack:
				return R.anim.activity_trans_scaleback_out;
			case Dialog:
				return R.anim.activity_trans_none;
			default:
				return 0;
		}
	}

	private int getOutEnterAnimation(AnimationStyle animationStyle) {
		switch(animationStyle) {
			case SlideSideways:
				return R.anim.activity_trans_nudge_right;
			case SlideUp:
				return R.anim.activity_trans_scaleup;
			case FadeBack:
				return R.anim.activity_trans_scaleup_in;
			case Dialog:
				return R.anim.activity_trans_none;
			default:
				return 0;
		}
	}

	private int getOutExitAnimation(AnimationStyle animationStyle) {
		switch(animationStyle) {
			case SlideSideways:
				return R.anim.activity_trans_slide_right_out;
			case SlideUp:
				return R.anim.activity_trans_slide_down_out;
			case FadeBack:
			case Dialog:
				return R.anim.activity_trans_scaleup_out;
			default:
				return 0;
		}
	}

}
