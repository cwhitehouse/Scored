package kippy.android.scored.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kippy.android.scored.activity.MyActivity;

/**
 * Created by christianwhitehouse on 6/20/14.
 */
public abstract class MyFragment extends Fragment {

	List<BroadcastReceiverInfo> mRegisteredReceivers = new ArrayList<BroadcastReceiverInfo>();

	View vBase;

	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
		vBase = layoutInflater.inflate(getLayoutID(), container, false);
		return vBase;
	}

	@Override
	public void onStart() {
		super.onStart();

		LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
		for(BroadcastReceiverInfo registeredReceiver : mRegisteredReceivers)
			localBroadcastManager.registerReceiver(registeredReceiver.mBroadcastReceiver, new IntentFilter(registeredReceiver.mBroadcastAction));
	}

	@Override
	public void onStop() {
		super.onStop();

		LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
		for(BroadcastReceiverInfo registeredReceiver : mRegisteredReceivers)
			localBroadcastManager.unregisterReceiver(registeredReceiver.mBroadcastReceiver);
	}

	public abstract int getLayoutID();

	public MyActivity getMyActivity() {
		return (MyActivity)getActivity();
	}

	public void registerReceiver(BroadcastReceiver broadcastReceiver, String broadcastAction) {
		mRegisteredReceivers.add(new BroadcastReceiverInfo(broadcastReceiver, broadcastAction));
	}

	public boolean onBackPressed() {
		return false;
	}

	private class BroadcastReceiverInfo {
		private BroadcastReceiver mBroadcastReceiver;
		private String mBroadcastAction;

		public BroadcastReceiverInfo(BroadcastReceiver broadcastReceiver, String broadcastAction) {
			mBroadcastReceiver = broadcastReceiver;
			mBroadcastAction = broadcastAction;
		}
	}

}
