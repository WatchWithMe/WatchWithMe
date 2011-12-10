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
import com.utcn.watchwithme.objects.Showtime;

public class ShowtimeAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<Showtime> items;
	private Resources res;

	public ShowtimeAdapter(Context context, ArrayList<Showtime> items) {
		// Cache the LayoutInflate to avoid asking for a new one each time.
		mInflater = LayoutInflater.from(context);
		this.items = items;
		this.res = context.getResources();
	}

	public void setItems(ArrayList<Showtime> items) {
		this.items = items;
		notifyDataSetChanged();
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.showtime_list_item, null);

			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.showtime_image);
			holder.title = (TextView) convertView
					.findViewById(R.id.showtime_title);
			holder.hours = (TextView) convertView
					.findViewById(R.id.showtime_hours);
			holder.price = (TextView) convertView
					.findViewById(R.id.showtime_price);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// Bind the data efficiently with the holder.
		Showtime showtime = items.get(position);
		if (showtime.getMovie().getIcon() != -1)
			holder.image.setImageDrawable(res.getDrawable(showtime.getMovie()
					.getIcon()));
		holder.title.setText(showtime.getMovie().getTitle());
		holder.hours.setText(showtime.getHours());
		holder.price.setText(showtime.getPriceString());

		return convertView;
	}

	static class ViewHolder {
		ImageView image;
		TextView title;
		TextView hours;
		TextView price;
	}
}
