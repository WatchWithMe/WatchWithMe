package com.utcn.watchwithme.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.adapters.MoviesAdapter;
import com.utcn.watchwithme.objects.Movie;
import com.utcn.watchwithme.repository.Utilities;

public class MovieGridActivity extends Activity {
	private MoviesAdapter mAdapter;
	private GridView mGridView;
	private ArrayList<Movie> movies = Utilities.getAllMovies();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_grid_layout);
		setUpViews();
		setUpAdapter();

		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				goToMovieActivity(position);
			}
		});
	}

	private void setUpViews() {
		mGridView = (GridView) findViewById(R.id.grid);
	}

	private void setUpAdapter() {
		mAdapter = new MoviesAdapter(this, movies);
		mGridView.setAdapter(mAdapter);
	}

	private void goToMovieActivity(int position) {
		Utilities.setSelectedMovie(movies.get(position));

		Intent intent = new Intent(this, MovieActivity.class);
		startActivity(intent);
	}
}
