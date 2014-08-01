package kippy.android.scored.animation;

import android.view.animation.Interpolator;

/**
 * Created by christianwhitehouse on 7/8/14.
 */
public class DelayedInterpolator implements Interpolator {

	private Interpolator mDelayedInterpolator;

	private float mPercentDelayed;
	private float mScaleFactor;

	public DelayedInterpolator(Interpolator delayedInterpolator) {
		this(delayedInterpolator, 0.5f);
	}

	public DelayedInterpolator(Interpolator delayedInterpolator, long delayedTime, long animatedTime) {
		this(delayedInterpolator, (float)delayedTime / (float)(delayedTime + animatedTime));
	}

	public DelayedInterpolator(Interpolator delayedInterpolator, float percentDelayed) {
		mDelayedInterpolator = delayedInterpolator;

		mPercentDelayed = percentDelayed;
		mScaleFactor = 1f/(1f-mPercentDelayed);
	}

	@Override
	public float getInterpolation(float input) {
		if(input <= mPercentDelayed)
			return 0;
		else
			return mDelayedInterpolator.getInterpolation((input-mPercentDelayed) * mScaleFactor);
	}
}
