package kippy.android.scored.ui.holder;

import android.view.View;

/**
 * Created by christianwhitehouse on 6/20/14.
 */
public abstract class ViewHolder {

	int mLastPosition;
	public View vBase;

	public ViewHolder(View convertView, int position) {
		vBase = convertView;
		vBase.setTag(this);

		mLastPosition = position;
	}

	public void resetViewHolder(int newPosition) {
		if(mLastPosition != newPosition)
			reset();
	}

	protected abstract void reset();
}
