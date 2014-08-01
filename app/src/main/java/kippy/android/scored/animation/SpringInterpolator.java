package kippy.android.scored.animation;

import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by christianwhitehouse on 7/7/14.
 */
public class SpringInterpolator implements Interpolator {

	private static final float FIRST_STAGE_CURVE_ADJUST_MULTIPLIER = 1.1f;

	private static final float FIRST_STAGE_LENGTH_MULTIPLIER = 0.75f;
	private static final float FIRST_STAGE_INPUT_MULTIPLIER = 1f/FIRST_STAGE_LENGTH_MULTIPLIER;

	private float mFirstStageCutoff;
	private float mSecondStageMultiplier;

	private float mFirstStageMaximum;

	float mTension = -1;

	private Interpolator mFirstStageInterpolator;
	private Interpolator mSecondStageInterpolator;

	public SpringInterpolator() {
		this(2.0f);
	}

	public SpringInterpolator(float tension) {
		mTension = tension;

		mFirstStageInterpolator = new OvershootInterpolator(mTension);

		mFirstStageCutoff = getMaximum(mTension)*FIRST_STAGE_CURVE_ADJUST_MULTIPLIER;
		mFirstStageMaximum = mFirstStageInterpolator.getInterpolation(mFirstStageCutoff);

		mFirstStageCutoff *= FIRST_STAGE_LENGTH_MULTIPLIER;
		mSecondStageMultiplier = 1f/(1f - mFirstStageCutoff);

		mSecondStageInterpolator = new OvershootInterpolator(mTension*mSecondStageMultiplier*FIRST_STAGE_INPUT_MULTIPLIER);
	}

	@Override
	public float getInterpolation(float input) {
		float interpolation;
		if(input < mFirstStageCutoff) {
			float adjustedInput = input * FIRST_STAGE_INPUT_MULTIPLIER;
			interpolation = mFirstStageInterpolator.getInterpolation(adjustedInput);
		} else {
			float adjustedInput = Math.min((input - mFirstStageCutoff) * mSecondStageMultiplier, 1f);
			float interpolatedValue = mSecondStageInterpolator.getInterpolation(adjustedInput);
			interpolatedValue *= -1f;
			interpolatedValue *= (mFirstStageMaximum - 1f);
			interpolatedValue += mFirstStageMaximum;
			interpolation = interpolatedValue;
		}

		return interpolation;
	}

	private float getMaximum(float tension) {
		return quadraticFormula(3f*tension + 3f, -(4f*tension + 6f), tension + 3f);
	}

	private float quadraticFormula(float a, float b, float c) {
		float sqrt = (float) Math.sqrt(b * b - 4 * a * c);
		float rightOption = -b - sqrt;
		rightOption /= (2*a);

		return rightOption;
	}
}
