package com.utcn.watchwithme.activities;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
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
import com.utcn.watchwithme.services.CinemaService;

public class CinemaFavoritesListActivity extends ListActivity {

	private final String DEBUG_TAG = "CinemaFavoritesListActivity";

	private ArrayList<Cinema> items = CinemaService.getFavoriteCinemas();
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
}
