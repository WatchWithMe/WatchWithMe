package com.utcn.watchwithme.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.adapters.MoviesAdapter;
import com.utcn.watchwithme.repository.Utilities;

public class MovieGridActivity extends Activity {
	private MoviesAdapter mAdapter;
	private GridView mGridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_grid_layout);
		setUpViews();
		setUpAdapter();
	}

	private void setUpViews() {
		mGridView = (GridView) findViewById(R.id.grid);
	}

	private void setUpAdapter() {
		mAdapter = new MoviesAdapter(this, Utilities.getAllMovies());
		mGridView.setAdapter(mAdapter);
	}
}
