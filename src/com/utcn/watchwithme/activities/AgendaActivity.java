package com.utcn.watchwithme.activities;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.adapters.RemindersAdapter;
import com.utcn.watchwithme.objects.Reminder;
import com.utcn.watchwithme.services.AgendaService;
import com.utcn.watchwithme.services.MovieService;

public class AgendaActivity extends ListActivity {
	private RemindersAdapter mAdapter;
	private ArrayList<Reminder> mReminders;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agenda_layout);
		setUpAdapter();
	}

	private void setUpAdapter() {
		// TODO load from server/DB.
		mReminders = AgendaService.getReminders();
		mAdapter = new RemindersAdapter(this, mReminders);
		setListAdapter(mAdapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		goToMovieActivity(position);
	}

	private void goToMovieActivity(int position) {
		MovieService.setSelected(mReminders.get(position).getMovie());
		Intent intent = new Intent(this, MovieActivity.class);
		startActivity(intent);
	}

}
