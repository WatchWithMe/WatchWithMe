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
import com.utcn.watchwithme.objects.Cinema;
import com.utcn.watchwithme.objects.Movie;
import com.utcn.watchwithme.objects.Showtime;
import com.utcn.watchwithme.services.ImageService;

/**
 * 
 * @author Vlad
 * 
 */
public class ShowtimeAdapter extends MyBaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<Showtime> items;
	private boolean mForMovies;

	public ShowtimeAdapter(Context context, ArrayList<Showtime> items,
			boolean forMovies) {
		super(context);
		mInflater = LayoutInflater.from(context);
		this.items = items;
		this.mForMovies = forMovies;
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
		String imageUrl = null;
		String title = null;
		if (mForMovies) {
			// display info about movie
			Movie movie = showtime.getMovie();
			imageUrl = movie.getImageURL();
			title = movie.getTitle();
		} else {
			// display info about cinemas
			Cinema cinema = showtime.getCinema();
			imageUrl = cinema.getImageURL();
			title = cinema.getName();
		}
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
