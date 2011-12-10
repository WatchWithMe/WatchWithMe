package com.utcn.watchwithme.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class CinemaActivity extends Activity {

	private final String DEBUG_TAG = "CinemaActivity";
	private final int INVITE = 1;
	private final int REMIND = 2;
	private final int IGNORE = 3;
	private int menuSelection;

	private Cinema cinema = CinemaService.getSelected();
	private ArrayList<Showtime> showtimes = ShowtimeService.getForCinema(cinema
			.getId());
	private ShowtimeAdapter adapter;
	private Showtime pressedShowtime;
	private ListView listview;

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
		adapter = new ShowtimeAdapter(this, showtimes);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				goToMovieActivity(position);
			}
		});

		registerForContextMenu(listview);
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
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		if (v.getId() == R.id.showtime_list_view) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			pressedShowtime = showtimes.get(info.position);
			Log.i(DEBUG_TAG, "long pressed on "
					+ pressedShowtime.getMovie().getTitle());
			menu.setHeaderTitle(pressedShowtime.getMovie().getTitle());
			SubMenu inviteMenu = menu.addSubMenu(INVITE, INVITE, 0, "Invite");
			SubMenu remindMenu = menu
					.addSubMenu(REMIND, REMIND, 0, "Remind Me");
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
				// invite
				Log.e(DEBUG_TAG, "Implement the invitation");
				break;
			case REMIND:
				Reminder reminder = new Reminder(pressedShowtime,
						(String) item.getTitle());
				AgendaService.add(reminder);
				Log.i(DEBUG_TAG, reminder.toString());
				break;
			}
		}
		return super.onContextItemSelected(item);
	}

	private void ignoreConfirmation() {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage("Do you really want to ignore this movie ?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								MovieService.ignoreMovie(pressedShowtime
										.getMovie().getId());
								showtimes.remove(pressedShowtime);
								adapter.notifyDataSetChanged();
								Log.i(DEBUG_TAG, "Clicked on Yes Button");
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
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
}
