package com.utcn.watchwithme.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.objects.Movie;
import com.utcn.watchwithme.objects.Reminder;
import com.utcn.watchwithme.services.ImageService;

/**
 * 
 * @author Vlad
 * 
 */
public class RemindersAdapter extends MyBaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<Reminder> items;

	public RemindersAdapter(Context context, ArrayList<Reminder> items) {
		super(context);
		mInflater = LayoutInflater.from(context);
		this.items = items;
	}

	public void setItems(ArrayList<Reminder> items) {
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
			holder.image = (ImageView) convertView.findViewById(R.id.showtime_image);
			holder.title = (TextView) convertView.findViewById(R.id.showtime_title);
			holder.hours = (TextView) convertView.findViewById(R.id.showtime_hours);
			convertView.findViewById(R.id.showtime_price).setVisibility(View.GONE);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// Bind the data efficiently with the holder.
		Reminder reminder = items.get(position);
		String imageUrl = null;
		String title = null;
		// display info about movie
		Movie movie = reminder.getMovie();
		imageUrl = movie.getImageURL();
		title = movie.getTitle();
		ImageService is = ImageService.getInstance();
		if (imageUrl != null) {
			Bitmap bmp = is.getImage(imageUrl);
			if (bmp != null) {
				holder.image.setImageBitmap(bmp);
			} else {
				holder.image.setImageResource(R.drawable.no_image);
				loadImage(imageUrl);
			}
		}
		holder.title.setText(title);
		holder.hours.setText(reminder.getDate());

		return convertView;
	}

	static class ViewHolder {
		ImageView image;
		TextView title;
		TextView hours;
	}
}
