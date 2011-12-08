package com.utcn.watchwithme.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.adapters.MoviesAdapter;
import com.utcn.watchwithme.objects.Movie;
import com.utcn.watchwithme.repository.Utilities;

public class MovieGridActivity extends Activity {
	private MoviesAdapter mAdapter;
	private GridView mGridView;
	private EditText edittext;
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

		edittext.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					Toast.makeText(MovieGridActivity.this, edittext.getText(),
							Toast.LENGTH_SHORT).show();
					return true;
				}
				return false;
			}
		});
	}

	private void setUpViews() {
		edittext = (EditText) findViewById(R.id.edittext);
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
