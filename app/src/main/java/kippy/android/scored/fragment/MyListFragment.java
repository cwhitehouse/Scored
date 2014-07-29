package kippy.android.scored.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import kippy.android.scored.R;
import kippy.android.scored.ui.adapter.MyAdapter;

/**
 * Created by christianwhitehouse on 6/20/14.
 */
public class MyListFragment<T> extends MyFragment {

	ListView vList;
	MyAdapter<T,?> mAdapter;

	@Override
	public int getLayoutID() {
		return R.layout.my_list;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		vList = (ListView) view.findViewById(R.id.my_list);
	}
}
