package com.utcn.watchwithme.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.objects.Cinema;

public class CinemaAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<Cinema> items;
	private Resources res;

	public CinemaAdapter(Context context, ArrayList<Cinema> items) {
		// Cache the LayoutInflate to avoid asking for a new one each time.
		mInflater = LayoutInflater.from(context);
		this.res = context.getResources();
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setItems(ArrayList<Cinema> items) {
		this.items = items;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.cinema_list_item, null);

			holder = new ViewHolder();
			holder.icon = (ImageView) convertView
					.findViewById(R.id.cinema_icon);
			holder.name = (TextView) convertView.findViewById(R.id.cinema_name);
			holder.location = (TextView) convertView
					.findViewById(R.id.cinema_location);
			holder.favorite = (ImageView) convertView
					.findViewById(R.id.cinema_favorite);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// Bind the data efficiently with the holder.
		Cinema cinema = items.get(position);
		if (cinema.getIcon() != -1)
			holder.icon.setImageDrawable(res.getDrawable(cinema.getIcon()));
		holder.name.setText(cinema.getName());
		holder.location.setText(cinema.getLocation());
		if (cinema.isFavorite()) {
			holder.favorite.setImageDrawable(res
					.getDrawable(R.drawable.star_icon_selected));
		} else {
			holder.favorite.setImageDrawable(res
					.getDrawable(R.drawable.star_icon));
		}

		return convertView;
	}

	static class ViewHolder {
		ImageView icon;
		TextView name;
		TextView location;
		ImageView favorite;
	}
}
