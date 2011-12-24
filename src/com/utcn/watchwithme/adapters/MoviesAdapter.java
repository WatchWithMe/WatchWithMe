package com.utcn.watchwithme.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.objects.Movie;
import com.utcn.watchwithme.services.ImageService;

public class MoviesAdapter extends BaseAdapter {

	private ArrayList<Movie> mMovies;
	private Context mContext;

	public MoviesAdapter(Context context, ArrayList<Movie> movies) {
		this.mMovies = movies;
		mContext = context;
	}

	public void setItems(ArrayList<Movie> movies) {
		this.mMovies = movies;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mMovies.size();
	}

	@Override
	public Movie getItem(int position) {
		return mMovies.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Movie movie = getItem(position);
		if ((convertView == null) || (convertView.getTag() == null)) {
			convertView = View
					.inflate(mContext, R.layout.movie_list_item, null);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView
					.findViewById(R.id.movie_item_icon);
			holder.name = (TextView) convertView
					.findViewById(R.id.movie_item_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ImageService is = ImageService.getInstance();
		String imageURL = movie.getImageURL();
		if (imageURL != null) {
			Bitmap bmp = is.getImage(imageURL);
			if (bmp != null) {
				holder.icon.setImageBitmap(bmp);
			} else {
				holder.icon.setImageResource(R.drawable.no_image);
			}
		} else {
			if (movie.getIcon() == -1) {
				holder.icon.setImageResource(R.drawable.no_image);
			} else {
				holder.icon.setImageResource(movie.getIcon());
			}
		}
		holder.name.setText(movie.getTitle());
		return convertView;
	}

	class ViewHolder {
		ImageView icon;
		TextView name;
	}
}
