package com.utcn.watchwithme.activities;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.utcn.watchwithme.adapters.CinemaAdapter;
import com.utcn.watchwithme.objects.Cinema;
import com.utcn.watchwithme.repository.Utilities;

public class CinemaListActivity extends ListActivity {

	private final String DEBUG_TAG = "CinemaListActivity";

	private ArrayList<Cinema> items = Utilities.getAllCinemas();
	private CinemaAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		adapter = new CinemaAdapter(this, items);
		setListAdapter(adapter);

		ListView lv = getListView();
		Log.i(DEBUG_TAG, "ListView = " + lv);
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
		adapter = new CinemaAdapter(this, items);
		setListAdapter(adapter);
	}

	private void goToCinemaActivity(int position) {
		Utilities.setSelectedCinema(items.get(position));

		Intent intent = new Intent(this, CinemaActivity.class);
		startActivity(intent);
	}

	public void clickedFavorites(View v) {
		RelativeLayout parent = (RelativeLayout) v.getParent();

		ListView lvItems = getListView();
		for (int i = 0; i < lvItems.getChildCount(); i++) {
			RelativeLayout rl = (RelativeLayout) lvItems.getChildAt(i);
			if (rl == parent) {
				Utilities.changeCinemaFavoriteStatus(i, 1);
				Log.i(DEBUG_TAG, "Changed cinema " + i + " favorite status");
			}
		}

		items = Utilities.getAllCinemas();
		adapter = new CinemaAdapter(this, items);
		setListAdapter(adapter);
	}
}
