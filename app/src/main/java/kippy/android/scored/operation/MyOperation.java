package kippy.android.scored.operation;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by christianwhitehouse on 6/20/14.
 */
public abstract class MyOperation extends AsyncTask<Void,Void,Integer> {

	//================================================================================
	// Constants
	//================================================================================

	public static final int RESULT_SUCCESS = 0;
	public static final int RESULT_NETWORK_FAILURE = 1;
	public static final int RESULT_OPERATION_FAILURE = 2;

	private static final String RESULT_CODE = "result_code";

	//================================================================================
	// Class Variables
	//================================================================================

	private Context mContext;

	//================================================================================
	// Constructor
	//================================================================================

	public MyOperation(Context context) {
		mContext = context;
	}

	//================================================================================
	// Implementation
	//================================================================================

	@Override
	public void onPostExecute(Integer result) {
		super.onPostExecute(result);

		OperationDispatch.getInstance().onOperationFinished(this);
		sendResult(getBroadcastAction(), result);
	}

	//================================================================================
	// To Implement
	//================================================================================

	public abstract String getBroadcastAction();

	//================================================================================
	// Utilities
	//================================================================================

	public Context getContext() {
		return mContext;
	}

	//================================================================================
	// Sending Results
	//================================================================================

	protected void sendResult(String action, int resultCode) {
		sendResultIntent(buildResultIntent(action,resultCode));
	}

	protected boolean sendResultIntent(Intent intent) {
		return LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
	}

	protected Intent buildResultIntent(String action, int resultCode) {
		Intent resultIntent = new Intent(action);
		resultIntent.putExtra(RESULT_CODE, resultCode);
		addResultData(resultIntent);
		return resultIntent;
	}

	protected void addResultData(Intent resultIntent) {}

	//================================================================================
	// Static Utility
	//================================================================================

	public static int getResultCode(Intent broadcastIntent) {
		return broadcastIntent.getIntExtra(RESULT_CODE, RESULT_NETWORK_FAILURE);
	}

	public static boolean wasSuccessful(Intent broadcastIntent) {
		return getResultCode(broadcastIntent) == RESULT_SUCCESS;
	}

}
