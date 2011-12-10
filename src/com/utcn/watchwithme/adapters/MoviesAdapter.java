package com.utcn.watchwithme.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.objects.Movie;

public class MoviesAdapter extends BaseAdapter {

	private ArrayList<Movie> mMovies;
	private Context mContext;

	public MoviesAdapter(Context context, ArrayList<Movie> movies) {
		this.mMovies = movies;
		mContext = context;
	}

	public int getCount() {
		return mMovies.size();
	}

	public Movie getItem(int position) {
		return mMovies.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

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
		holder.icon.setBackgroundResource(movie.getIcon());
		holder.name.setText(movie.getTitle());
		return convertView;
	}

	class ViewHolder {
		ImageView icon;
		TextView name;
	}
}
