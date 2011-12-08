package com.utcn.watchwithme.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.adapters.MoviesAdapter;
import com.utcn.watchwithme.objects.Movie;
import com.utcn.watchwithme.repository.Utilities;

public class MovieGridActivity extends Activity {

	private static final String DEBUG_TAG = "MovieGridActivity";
	private static final int IGNORE = 1;

	private MoviesAdapter mAdapter;
	private GridView mGridView;
	private EditText edittext;
	private ArrayList<Movie> movies = Utilities.getAllMovies();
	private Movie pressedMovie;

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
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					movies = Utilities.searchForMovie(edittext.getText()
							.toString());
					setUpAdapter();
					return true;
				}
				return false;
			}
		});

		registerForContextMenu(mGridView);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.movies_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.unignore1_menu_option:
			Utilities.unignoreMovies();
			movies = Utilities.searchForMovie(edittext.getText().toString());
			setUpAdapter();
			break;
		}
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		if (v.getId() == R.id.grid) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			pressedMovie = movies.get(info.position);
			Log.i(DEBUG_TAG, "long pressed on " + pressedMovie.getTitle());
			menu.setHeaderTitle(pressedMovie.getTitle());
			menu.add(IGNORE, IGNORE, 0, "Ignore Movie");
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Log.i(DEBUG_TAG, (String) item.getTitle());
		switch (item.getGroupId()) {
		case IGNORE:
			ignoreConfirmation();
			break;
		}
		return super.onContextItemSelected(item);
	}

	private void ignoreConfirmation() {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage("Do you really want to ignore this movie ?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Utilities.ignoreMovie(pressedMovie);
								movies.remove(pressedMovie);
								setUpAdapter();
								Log.i(DEBUG_TAG, "Clicked on Yes Button");
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Log.i(DEBUG_TAG, "Clicked on No Button");
						dialog.cancel();
					}
				});
		AlertDialog alert = alt_bld.create();
		alert.setTitle("Ignore " + pressedMovie.getTitle());
		// Icon for AlertDialog
		alert.setIcon(R.drawable.icon);
		alert.show();
	}
}
