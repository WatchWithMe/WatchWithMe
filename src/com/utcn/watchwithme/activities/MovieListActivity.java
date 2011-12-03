package com.utcn.watchwithme.activities;

import android.app.ListActivity;
import android.os.Bundle;

import com.utcn.watchwithme.R;

public class MovieListActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_list_layout);
	}
}
