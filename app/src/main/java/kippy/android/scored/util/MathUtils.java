package kippy.android.scored.util;

/**
 * Created by christianwhitehouse on 7/30/14.
 */
public class MathUtils {

	public static int randomInt(int min, int max) {
		int range = max-min + 1;

		double rand = Math.random();
		rand = rand*range;

		int result = (int) Math.floor(rand);
		return result + min;
	}

	public static float interpolateValue(float inValue, float inMin, float inMax, float outMin, float outMax) {
		float inRange = inMax - inMin;
		float outRange = outMax - outMin;

		float multiplier = outRange/inRange;

		float outRealMin = Math.min(outMin, outMax);
		float outRealMax = Math.max(outMin, outMax);

		return Math.min(Math.max((inValue-inMin)*multiplier + outMin, outRealMin), outRealMax);
	}

}
