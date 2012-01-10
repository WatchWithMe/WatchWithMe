package com.utcn.watchwithme.activities;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.adapters.ShowtimeAdapter;
import com.utcn.watchwithme.objects.Cinema;
import com.utcn.watchwithme.objects.Reminder;
import com.utcn.watchwithme.objects.Showtime;
import com.utcn.watchwithme.services.AgendaService;
import com.utcn.watchwithme.services.CinemaService;
import com.utcn.watchwithme.services.MovieService;
import com.utcn.watchwithme.services.ShowtimeService;

/**
 * 
 * @author Vlad Baja
 * 
 */
public class CinemaActivity extends Activity {

	private final String DEBUG_TAG = "CinemaActivity";
	private static final String PROGRESS_MESSAGE = "Please wait while loading...";
	private final int INVITE = 1;
	private final int REMIND = 2;
	private final int IGNORE = 3;
	private int menuSelection;

	private Cinema cinema = CinemaService.getSelected();
	private ArrayList<Showtime> showtimes = new ArrayList<Showtime>();
	private ShowtimeAdapter adapter;
	private Showtime pressedShowtime;
	private ListView listview;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cinema_details_layout);

		TextView tv;
		tv = (TextView) findViewById(R.id.cinema_full_name);
		tv.setText(cinema.getName());
		tv = (TextView) findViewById(R.id.cinema_full_location);
		tv.setText(cinema.getLocation());

		listview = (ListView) findViewById(R.id.showtime_list_view);
		adapter = new ShowtimeAdapter(this, showtimes, true);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				goToMovieActivity(position);
			}
		});

		registerForContextMenu(listview);
		dialog = ProgressDialog.show(this, "", PROGRESS_MESSAGE, true);
		new LoadTask(this).execute();
	}

	private void refreshContent() {
		showtimes = ShowtimeService.getForCinema(cinema.getId());
		adapter.setItems(showtimes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.cinema_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.unignore_menu_option:
			MovieService.unignoreMovies();
			refreshContent();
			break;
		case R.id.cinema_sync_menu_option:
			dialog = ProgressDialog.show(this, "", PROGRESS_MESSAGE, true);
			ShowtimeService.eraseData();
			showtimes.clear();
			adapter.notifyDataSetChanged();
			new LoadTask(this).execute();
			break;
		case R.id.name_sort_menu_option:
			ShowtimeService.sortByTitle();
			refreshContent();
			break;
		case R.id.price_sort_menu_option:
			ShowtimeService.sortByPrice();
			refreshContent();
			break;
		}
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		if (v.getId() == R.id.showtime_list_view) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			pressedShowtime = showtimes.get(info.position);
			Log.i(DEBUG_TAG, "long pressed on " + pressedShowtime.getMovie().getTitle());
			menu.setHeaderTitle(pressedShowtime.getMovie().getTitle());
			SubMenu inviteMenu = menu.addSubMenu(INVITE, INVITE, 0, "Invite");
			SubMenu remindMenu = menu.addSubMenu(REMIND, REMIND, 0, "Remind Me");
			String dates[] = pressedShowtime.getShowtimes();
			for (int i = 0; i < dates.length; i++) {
				inviteMenu.add(dates[i]);
				remindMenu.add(dates[i]);
			}
			menu.add(IGNORE, IGNORE, 0, "Ignore Movie");
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Log.i(DEBUG_TAG, (String) item.getTitle());
		switch (item.getGroupId()) {
		case INVITE:
			menuSelection = INVITE;
			break;
		case REMIND:
			menuSelection = REMIND;
			break;
		case IGNORE:
			ignoreConfirmation();
			break;
		default:
			switch (menuSelection) {
			case INVITE:
				sendEmail(pressedShowtime.getMovie().getTitle(), (String) item.getTitle());
				Log.e(DEBUG_TAG, "Implement the invitation");
				break;
			case REMIND:
				Reminder reminder = new Reminder(pressedShowtime, (String) item.getTitle());
				AgendaService.add(reminder);
				// TODO Store to DB and push changes to server.
				Log.i(DEBUG_TAG, reminder.toString());
				break;
			}
		}
		return super.onContextItemSelected(item);
	}

	private void sendEmail(String movie, String date) {
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "", });
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Invitation to a movie");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey,\n\n Do you want to come to the movie \"" + movie + "\" on " + date + "?");
		emailIntent.setType("text/plain");
		startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	}

	private void ignoreConfirmation() {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage("Do you really want to ignore this movie ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				MovieService.ignoreMovie(pressedShowtime.getMovie().getId());
				showtimes.remove(pressedShowtime);
				adapter.notifyDataSetChanged();
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
		alert.setTitle("Ignore " + pressedShowtime.getMovie().getTitle());
		// Icon for AlertDialog
		alert.setIcon(R.drawable.icon);
		alert.show();
	}

	private void goToMovieActivity(int position) {
		MovieService.setSelected(showtimes.get(position).getMovie());

		Intent intent = new Intent(this, MovieActivity.class);
		startActivity(intent);
	}

	public void goToMap(View v) {
		Intent intent = new Intent(this, GoogleMapsActivity.class);
		startActivity(intent);
	}

	public void dismissLoadingDialog() {
		dialog.dismiss();
	}

	private void changeContent(ArrayList<Showtime> sts) {
		this.showtimes = sts;
		adapter.setItems(sts);
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

	private static class LoadTask extends AsyncTask<Void, Void, ArrayList<Showtime>> {

		private WeakReference<CinemaActivity> weakActivity;

		public LoadTask(CinemaActivity activity) {
			this.weakActivity = new WeakReference<CinemaActivity>(activity);
		}

		@Override
		protected ArrayList<Showtime> doInBackground(Void... params) {
			return ShowtimeService.getForCinema(CinemaService.getSelected().getId());
		}

		@Override
		protected void onPostExecute(ArrayList<Showtime> sts) {
			weakActivity.get().dismissLoadingDialog();
			if (sts != null) {
				weakActivity.get().changeContent(sts);
				if (!ShowtimeService.updated()) {
					weakActivity.get().showAlert("Connection to the server failed.\n" + "Data might be outdated.");
				}
			}
		}
	}
}
