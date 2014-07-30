package kippy.android.scored.activity;

import kippy.android.scored.fragment.MinigolfFragment;

/**
 * Created by christianwhitehouse on 7/30/14.
 */
public class MinigolfActivity extends MyActivity<MinigolfFragment> {

	@Override
	public MinigolfFragment createFragment() {
		return new MinigolfFragment();
	}
}
