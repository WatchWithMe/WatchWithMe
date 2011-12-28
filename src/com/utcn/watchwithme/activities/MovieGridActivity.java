package com.utcn.watchwithme.activities;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.adapters.MoviesAdapter;
import com.utcn.watchwithme.objects.Movie;
import com.utcn.watchwithme.services.ImageService;
import com.utcn.watchwithme.services.MovieService;

public class MovieGridActivity extends Activity {

	private static final String DEBUG_TAG = "MovieGridActivity";
	private static final int IGNORE = 1;

	private MoviesAdapter mAdapter;
	private GridView mGridView;
	private EditText edittext;
	private ArrayList<Movie> movies = new ArrayList<Movie>();
	private Movie pressedMovie;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_grid_layout);
		setUpViews();
		setUpAdapter();

		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				goToMovieActivity(position);
			}
		});

		edittext.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				movies = MovieService.searchForMovie(edittext.getText().toString());
				mAdapter.setItems(movies);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
		// edittext.setOnKeyListener(new OnKeyListener() {
		// @Override
		// public boolean onKey(View v, int keyCode, KeyEvent event) {
		// if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode ==
		// KeyEvent.KEYCODE_ENTER)) {
		// movies = MovieService.searchForMovie(edittext.getText().toString());
		// mAdapter.setItems(movies);
		// return true;
		// }
		// return false;
		// }
		// });

		registerForContextMenu(mGridView);

		dialog = ProgressDialog.show(this, "", "Please wait for few seconds...", true);
		new LoadTask(this).execute();
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
		MovieService.setSelected(movies.get(position));

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
			MovieService.unignoreMovies();
			movies = MovieService.searchForMovie(edittext.getText().toString());
			mAdapter.setItems(movies);
			break;
		case R.id.movies_sync_menu_option:
			MovieService.eraseData();
			movies.clear();
			mAdapter.notifyDataSetChanged();
			new LoadTask(this).execute();
			break;
		}
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
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
		alt_bld.setMessage("Do you really want to ignore this movie ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				MovieService.ignoreMovie(pressedMovie.getId());
				movies.remove(pressedMovie);
				mAdapter.setItems(movies);
				Log.i(DEBUG_TAG, "Clicked on Yes Button");
			}
		}).setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
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

	public void dismissLoadingDialog() {
		dialog.dismiss();
	}

	private void changeContent(ArrayList<Movie> movies) {
		this.movies = movies;
		mAdapter.setItems(movies);
	}

	private void notifyAdapter() {
		mAdapter.notifyDataSetChanged();
	}

	private void showAlert(String message) {
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setMessage(message);
		ad.setButton("OK", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		ad.show();
	}

	private static class LoadTask extends AsyncTask<Void, Void, ArrayList<Movie>> {

		private WeakReference<MovieGridActivity> weakActivity;

		public LoadTask(MovieGridActivity activity) {
			this.weakActivity = new WeakReference<MovieGridActivity>(activity);
		}

		@Override
		protected ArrayList<Movie> doInBackground(Void... params) {
			ArrayList<Movie> mList = MovieService.getAllMovies();
			return mList;
		}

		@Override
		protected void onPostExecute(ArrayList<Movie> movies) {
			weakActivity.get().dismissLoadingDialog();
			if (movies != null) {
				weakActivity.get().changeContent(movies);
				if (!MovieService.updated()) {
					weakActivity.get().showAlert("Connection to the server failed.\n" + "Data might be outdated.");
				}
				for (Movie m : movies) {
					if (m.getImageURL() != null) {
						new ImageTask(weakActivity.get()).execute(m.getImageURL());
					}
				}
			}
		}
	}

	private static class ImageTask extends AsyncTask<String, Exception, Bitmap> {

		private WeakReference<MovieGridActivity> weakActivity;

		public ImageTask(MovieGridActivity activity) {
			this.weakActivity = new WeakReference<MovieGridActivity>(activity);
		}

		@Override
		protected Bitmap doInBackground(String... urls) {
			try {
				ImageService is = ImageService.getInstance();
				return is.loadImage(urls[0]);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(Bitmap bmp) {
			try {
				if (bmp != null) {
					weakActivity.get().notifyAdapter();
				}
			} catch (Exception e) {
				Log.e("ImageTask", "error here");
			}
		}
	}
}
