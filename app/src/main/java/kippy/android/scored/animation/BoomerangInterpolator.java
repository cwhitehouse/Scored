package kippy.android.scored.animation;

import android.view.animation.Interpolator;

/**
 * Created by christianwhitehouse on 7/8/14.
 */
public class BoomerangInterpolator implements Interpolator {

	private Interpolator mThereInterpolator;
	private Interpolator mBackAgainInterpolator;

	float mTherePercentage;
	float mThereMultiplier;
	float mBackAgainMultiplier;

	public BoomerangInterpolator(Interpolator thereInterpolator, Interpolator backAgainInterpolator) {
		this(thereInterpolator, backAgainInterpolator, 0.5f);
	}

	public BoomerangInterpolator(Interpolator thereInterpolator, Interpolator backAgainInterpolator, long thereTime, long backTime) {
		this(thereInterpolator, backAgainInterpolator, (float)thereTime / (float)(thereTime + backTime));
	}

	public BoomerangInterpolator(Interpolator thereInterpolator, Interpolator backAgainInterpolator, float therePercentage) {
		mThereInterpolator = thereInterpolator;
		mBackAgainInterpolator = backAgainInterpolator;

		mTherePercentage = therePercentage;
		mThereMultiplier = 1f/therePercentage;
		mBackAgainMultiplier = 1f/(1f-therePercentage);
	}

	@Override
	public float getInterpolation(float input) {
		if(input < mTherePercentage)
			return mThereInterpolator.getInterpolation(input*mThereMultiplier);
		else {
			float adjustedInput = (input - mTherePercentage) * mBackAgainMultiplier;
			float interpolatedValue = mBackAgainInterpolator.getInterpolation(adjustedInput);
			interpolatedValue *= -1f;
			interpolatedValue += 1f;
			return interpolatedValue;
		}
	}
}
