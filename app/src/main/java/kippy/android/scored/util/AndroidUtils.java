package kippy.android.scored.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.FloatMath;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by christianwhitehouse on 6/20/14.
 */
public class AndroidUtils {

	public static void showKeyboard(Context c, View v) {
		if(v != null && c != null){
			InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
		}
	}

	public static void hideKeyboard(Context c, View v) {
		if(v != null && c != null){
			InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
	}

	public static int convertDipToPix(Context context, int dips) {
		float calculatedValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dips, context.getResources().getDisplayMetrics());
		return (int) FloatMath.ceil(calculatedValue);
	}

	public static String getAppVersion(Context context) {
		String appVersion = "???";
		if(context.getPackageManager() != null) {
			try { appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName; }
			catch(PackageManager.NameNotFoundException e) { throw new RuntimeException(e); }
		}

		return appVersion;
	}

}
