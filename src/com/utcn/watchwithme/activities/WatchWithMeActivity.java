package com.utcn.watchwithme.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.services.CinemaService;
import com.utcn.watchwithme.services.MovieService;
import com.utcn.watchwithme.services.ShowtimeService;

public class WatchWithMeActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private Button mButtonCinemas;
	private Button mButtonMovies;
	private Button mButtonNotifications;
	private Button mButtonReminders;

	private static Activity instance;

	public static Activity getInstance() {
		return instance;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		setContentView(R.layout.main);
		setUpViews();
	}

	private void setUpViews() {
		mButtonCinemas = (Button) findViewById(R.id.button_cinema);
		mButtonCinemas.setOnClickListener(this);

		mButtonMovies = (Button) findViewById(R.id.button_movies);
		mButtonMovies.setOnClickListener(this);

		mButtonNotifications = (Button) findViewById(R.id.button_invitations);
		mButtonNotifications.setOnClickListener(this);

		mButtonReminders = (Button) findViewById(R.id.button_reminders);
		mButtonReminders.setOnClickListener(this);

	}

	private void onCinemasButtonPressed() {
		/* Handle click here */
		Intent intent = new Intent(this, CinemaTabWidget.class);
		startActivity(intent);
	}

	private void onMoviesButtonPressed() {
		/* Start movies activity */
		Intent intent = new Intent(this, MovieGridActivity.class);
		startActivity(intent);
	}

	private void onNotificationsButtonPressed() {
		/* Handle click here */
		Intent intent = new Intent(this, AgendaActivity.class);
		startActivity(intent);
	}

	private void onRemindersButtonPressed() {
		/* Handle click here */
		Intent intent = new Intent(this, AgendaActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_cinema:
			onCinemasButtonPressed();
			break;
		case R.id.button_movies:
			onMoviesButtonPressed();
			break;
		case R.id.button_invitations:
			onNotificationsButtonPressed();
			break;
		case R.id.button_reminders:
			onRemindersButtonPressed();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.erase_menu_option:
			CinemaService.eraseData();
			MovieService.eraseData();
			ShowtimeService.eraseData();
			break;
		}
		return true;
	}
}