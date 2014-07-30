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

}
