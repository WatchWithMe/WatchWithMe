package com.utcn.watchwithme.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.utcn.watchwithme.R;

public class CinemaTabWidget extends TabActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cinema_tab_layout);

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, CinemaListActivity.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("all")
				.setIndicator("All", res.getDrawable(R.drawable.star_icon))
				.setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, CinemaFavoritesListActivity.class);
		spec = tabHost
				.newTabSpec("favorites")
				.setIndicator("Favorites",
						res.getDrawable(R.drawable.star_icon_selected))
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(1);
	}
}
