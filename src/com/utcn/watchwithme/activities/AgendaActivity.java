package com.utcn.watchwithme.activities;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.adapters.RemindersAdapter;
import com.utcn.watchwithme.objects.Reminder;
import com.utcn.watchwithme.services.AgendaService;
import com.utcn.watchwithme.services.MovieService;

public class AgendaActivity extends ListActivity {

	private static final String PROGRESS_MESSAGE = "Please wait while loading...";

	private RemindersAdapter mAdapter;
	private ArrayList<Reminder> mReminders;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agenda_layout);
		setUpAdapter();

		dialog = ProgressDialog.show(this, "", PROGRESS_MESSAGE, true);
		new LoadTask(this).execute();
	}

	private void setUpAdapter() {
		mReminders = new ArrayList<Reminder>();
		mAdapter = new RemindersAdapter(this, mReminders);
		setListAdapter(mAdapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		goToMovieActivity(position);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.agenda_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete_agenda_menu_option:
			AgendaService.deleteAll();
			mAdapter.notifyDataSetChanged();
			break;
		}
		return true;
	}

	private void goToMovieActivity(int position) {
		MovieService.setSelected(mReminders.get(position).getMovie());
		Intent intent = new Intent(this, MovieActivity.class);
		startActivity(intent);
	}

	public void dismissLoadingDialog() {
		dialog.dismiss();
	}

	private void changeContent(ArrayList<Reminder> rems) {
		this.mReminders = rems;
		this.mAdapter.setItems(rems);
	}

	private static class LoadTask extends
			AsyncTask<Void, Void, ArrayList<Reminder>> {

		private WeakReference<AgendaActivity> weakActivity;

		public LoadTask(AgendaActivity activity) {
			this.weakActivity = new WeakReference<AgendaActivity>(activity);
		}

		@Override
		protected ArrayList<Reminder> doInBackground(Void... params) {
			return AgendaService.getReminders();
		}

		@Override
		protected void onPostExecute(ArrayList<Reminder> rems) {
			weakActivity.get().dismissLoadingDialog();
			if (rems != null) {
				weakActivity.get().changeContent(rems);
			}
		}
	}

}
