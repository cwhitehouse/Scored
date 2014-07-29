package kippy.android.scored.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import kippy.android.scored.ui.holder.ViewHolder;

/**
 * Created by christianwhitehouse on 6/20/14.
 */
public abstract class MyAdapter<T, V extends ViewHolder> extends ArrayAdapter<T> {

	//================================================================================
	// Class Variables
	//================================================================================

	protected List<T> mItems;

	//================================================================================
	// Constructor
	//================================================================================

	public MyAdapter(Context context, List<T> items) {
		super(context, 0);
		mItems = items;
	}

	//================================================================================
	// Implementation
	//================================================================================

	@Override
	public int getCount() {
		return mItems == null ? 0 : mItems.size();
	}

	@Override
	public T getItem(int position) {
		if(mItems != null && position >= 0 && position < mItems.size())
			return mItems.get(position);
		else
			return null;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		V viewHolder = null;

		if(convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(getListItemLayoutID(), parent, false);
			viewHolder = createViewHolder(convertView, position);
		} else if(convertView.getTag() != null && convertView.getTag() instanceof ViewHolder) {
			viewHolder = (V) convertView.getTag();
			viewHolder.resetViewHolder(position);
		}

		T item = getItem(position);
		if(viewHolder != null && item != null)
			layoutItem(position, item, viewHolder);

		return convertView;
	}

	//================================================================================
	// Updating
	//================================================================================

	public void setItems(List<T> items) {
		mItems = items;
		notifyDataSetChanged();
	}

	//================================================================================
	// Abstract
	//================================================================================

	public abstract int getListItemLayoutID();
	public abstract V createViewHolder(View convertView, int position);
	public abstract void layoutItem(int position, T item, V viewHolder);
}
