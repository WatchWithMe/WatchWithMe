package com.utcn.watchwithme.activities;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.adapters.CinemaAdapter;
import com.utcn.watchwithme.objects.Cinema;
import com.utcn.watchwithme.services.CinemaService;

/**
 * 
 * @author Vlad Baja
 * 
 */
public class CinemaFavoritesListActivity extends ListActivity {

	private final String DEBUG_TAG = "CinemaFavoritesListActivity";
	private static final String PROGRESS_MESSAGE = "Please wait while loading...";

	private ArrayList<Cinema> items = new ArrayList<Cinema>();
	private ListView lv;
	private CinemaAdapter adapter;

	private ProgressDialog dialog;

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

		dialog = ProgressDialog.show(this, "", PROGRESS_MESSAGE, true);
		new LoadTask(this).execute();
	}

	@Override
	protected void onResume() {
		super.onResume();
		items = CinemaService.getFavoriteCinemas();
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.cinemas_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.cinemas_sync_menu_option:
			dialog = ProgressDialog.show(this, "", PROGRESS_MESSAGE, true);
			CinemaService.eraseData();
			items.clear();
			adapter.notifyDataSetChanged();
			new LoadTask(this).execute();
			break;
		}
		return true;
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

	public void dismissLoadingDialog() {
		dialog.dismiss();
	}

	private void changeContent(ArrayList<Cinema> cinemas) {
		this.items = cinemas;
		adapter.setItems(cinemas);
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
			weakActivity.get().dismissLoadingDialog();
			if (cinemas != null) {
				weakActivity.get().changeContent(cinemas);
				if (!CinemaService.updated()) {
					weakActivity.get().showAlert(
							"Connection to the server failed.\n"
									+ "Data might be outdated.");
				}
			}
		}
	}
}
