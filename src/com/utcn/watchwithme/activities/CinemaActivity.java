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
import android.view.KeyEvent;
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
import com.utcn.watchwithme.objects.Time;
import com.utcn.watchwithme.repository.Utilities;

public class CinemaActivity extends Activity {

	private final String DEBUG_TAG = "CinemaActivity";
	private final int INVITE = 1;
	private final int REMIND = 2;
	private final int IGNOR = 3;
	private int menuSelection;

	private Cinema cinema = Utilities.getSelectedCinema();
	private ArrayList<Showtime> showtimes = Utilities
			.getShowtimesForCinema(cinema);
	private Showtime pressedShowtime;
	private Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cinema_details_layout);
		activity = this;

		TextView tv;
		tv = (TextView) findViewById(R.id.cinema_full_name);
		tv.setText(cinema.getName());
		tv = (TextView) findViewById(R.id.cinema_full_location);
		tv.setText(cinema.getLocation());

		ListView lv = (ListView) findViewById(R.id.showtime_list_view);
		lv.setAdapter(new ShowtimeAdapter(this, showtimes));

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				goToMovieActivity(position);
			}
		});

		registerForContextMenu(lv);
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
			ArrayList<Time> dates = pressedShowtime.getDate();
			for (Time d : dates) {
				inviteMenu.add(d.toString());
				remindMenu.add(d.toString());
			}
			menu.add(IGNOR, IGNOR, 0, "Ignore Movie");
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
		case IGNOR:
			ignorConfirmation();
			break;
		default:
			switch (menuSelection) {
			case INVITE:
				// invite
				Log.e(DEBUG_TAG, "Implement the invitation");
				break;
			case REMIND:
				Reminder r = new Reminder(pressedShowtime,
						(String) item.getTitle());
				Utilities.addReminder(r);
				Log.i(DEBUG_TAG, r.toString());
				break;
			}
		}
		return super.onContextItemSelected(item);
	}

	private void ignorConfirmation() {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage("Do you really want to ignor this movie ?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Utilities.ignorMovie(pressedShowtime.getMovie());
								showtimes = Utilities
										.getShowtimesForCinema(cinema);
								ListView lv = (ListView) findViewById(R.id.showtime_list_view);
								lv.setAdapter(new ShowtimeAdapter(activity,
										showtimes));
								Log.i(DEBUG_TAG, "Clicked on Yes Button");
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Log.i(DEBUG_TAG, "Clicked on No Button");
						dialog.cancel();
					}
				});
		AlertDialog alert = alt_bld.create();
		alert.setTitle("Ignor " + pressedShowtime.getMovie().getTitle());
		// Icon for AlertDialog
		alert.setIcon(R.drawable.icon);
		alert.show();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			Log.i(DEBUG_TAG, "MENU pressed");
			Utilities.unignoreMovies();
			showtimes = Utilities.getShowtimesForCinema(cinema);
			((ListView) findViewById(R.id.showtime_list_view))
					.setAdapter(new ShowtimeAdapter(this, showtimes));
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void goToMovieActivity(int position) {
		Utilities.setSelectedMovie(showtimes.get(position).getMovie());

		Intent intent = new Intent(this, MovieActivity.class);
		startActivity(intent);
	}

	public void goToMap(View v) {
		Intent intent = new Intent(this, GoogleMapsActivity.class);
		startActivity(intent);
	}
}
