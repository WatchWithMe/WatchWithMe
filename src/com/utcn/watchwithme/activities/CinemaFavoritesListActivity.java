package com.utcn.watchwithme.activities;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.utcn.watchwithme.adapters.CinemaAdapter;
import com.utcn.watchwithme.objects.Cinema;
import com.utcn.watchwithme.repository.RemoteImageRepository;
import com.utcn.watchwithme.services.CinemaService;

/**
 * 
 * @author Vlad Baja
 * 
 */
public class CinemaFavoritesListActivity extends ListActivity {

	private final String DEBUG_TAG = "CinemaFavoritesListActivity";

	private ArrayList<Cinema> items = new ArrayList<Cinema>();
	private ListView lv;
	private CinemaAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		adapter = new CinemaAdapter(this, items);
		setListAdapter(adapter);

		lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i(DEBUG_TAG, "Clicked = " + position);
				goToCinemaActivity(position);
			}
		});

		new LoadTask(this).execute();
	}

	@Override
	protected void onResume() {
		super.onResume();
		items = CinemaService.getFavoriteCinemas();
		adapter.notifyDataSetChanged();
	}

	private void goToCinemaActivity(int position) {
		CinemaService.setSelected(items.get(position));

		Intent intent = new Intent(this, CinemaActivity.class);
		startActivity(intent);
	}

	public void clickedFavorites(View v) {
		FrameLayout parent = (FrameLayout) ((RelativeLayout) v.getParent())
				.getParent();

		Log.i(DEBUG_TAG, "ListView = " + lv);
		for (int i = 0; i < lv.getChildCount(); i++) {
			FrameLayout rl = (FrameLayout) lv.getChildAt(i);
			if (rl == parent) {
				CinemaService.changeCinemaFavoriteStatus(items.get(i).getId());
				Log.i(DEBUG_TAG, "Changed cinema " + i + " favorite status");
			}
		}

		items = CinemaService.getFavoriteCinemas();
		adapter.setItems(items);
	}

	private void changeContent(ArrayList<Cinema> cinemas) {
		this.items = cinemas;
		adapter.setItems(cinemas);
	}

	private void notifyAdapter() {
		adapter.notifyDataSetChanged();
	}

	private void showAlert(String message) {
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setMessage(message);
		ad.setButton("OK",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		ad.show();
	}

	private static class LoadTask extends
			AsyncTask<Void, Void, ArrayList<Cinema>> {

		private WeakReference<CinemaFavoritesListActivity> weakActivity;

		public LoadTask(CinemaFavoritesListActivity activity) {
			this.weakActivity = new WeakReference<CinemaFavoritesListActivity>(
					activity);
		}

		@Override
		protected ArrayList<Cinema> doInBackground(Void... params) {
			ArrayList<Cinema> mList = CinemaService.getFavoriteCinemas();
			return mList;
		}

		@Override
		protected void onPostExecute(ArrayList<Cinema> cinemas) {
			if (cinemas != null) {
				weakActivity.get().changeContent(cinemas);
				if (!CinemaService.updated()) {
					weakActivity.get().showAlert(
							"Connection to the server failed.\n"
									+ "Data might be outdated.");
				}
				for (Cinema c : cinemas) {
					new ImageTask(weakActivity.get()).execute(c.getImageURL());
				}
			}
		}
	}

	private static class ImageTask extends AsyncTask<String, Exception, Bitmap> {

		private WeakReference<CinemaFavoritesListActivity> weakActivity;

		public ImageTask(CinemaFavoritesListActivity activity) {
			this.weakActivity = new WeakReference<CinemaFavoritesListActivity>(
					activity);
		}

		@Override
		protected Bitmap doInBackground(String... urls) {
			try {
				return RemoteImageRepository.getBitmap(urls[0]);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(Bitmap bmp) {
			if (bmp != null) {
				weakActivity.get().notifyAdapter();
			}
		}
	}
}
