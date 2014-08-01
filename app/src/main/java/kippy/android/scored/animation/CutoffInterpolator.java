package kippy.android.scored.animation;

import android.view.animation.Interpolator;

/**
 * Created by christianwhitehouse on 7/8/14.
 */
public class CutoffInterpolator implements Interpolator {

	private Interpolator mCutoffInterpolator;

	float mCutoffPercentage;
	float mCutoffMultiplier;

	public CutoffInterpolator(Interpolator cutoffInterpolator) {
		this(cutoffInterpolator, 0.5f);
	}

	public CutoffInterpolator(Interpolator cutoffInterpolator, float cutoffPercentage) {
		mCutoffInterpolator = cutoffInterpolator;

		mCutoffPercentage = cutoffPercentage;
		mCutoffMultiplier = 1f/cutoffPercentage;
	}

	@Override
	public float getInterpolation(float input) {
		if(input >= mCutoffPercentage)
			return 1;
		else
			return mCutoffInterpolator.getInterpolation(input*mCutoffMultiplier);
	}
}
