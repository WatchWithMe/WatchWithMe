package com.utcn.watchwithme.repository;

import java.util.ArrayList;

import android.database.Cursor;
import android.util.Log;

import com.utcn.watchwithme.activities.WatchWithMeActivity;
import com.utcn.watchwithme.objects.Reminder;
import com.utcn.watchwithme.repository.database.ReminderDbAdapter;
import com.utcn.watchwithme.services.CinemaService;
import com.utcn.watchwithme.services.MovieService;

public class InternalReminderRepository {

	private ReminderDbAdapter helper;
	private ArrayList<Reminder> list;
	private static InternalReminderRepository instance;

	public static InternalReminderRepository getInstance() {
		if (instance == null) {
			instance = new InternalReminderRepository();
		}
		return instance;
	}

	private InternalReminderRepository() {
		helper = new ReminderDbAdapter(WatchWithMeActivity.getInstance());
		helper.open();
		list = new ArrayList<Reminder>();

		Cursor cursor = helper.fetchAllReminders();
		if (cursor != null) {
			while (cursor.moveToNext()) {
				int mid = cursor.getInt(cursor
						.getColumnIndexOrThrow(ReminderDbAdapter.KEY_MID));
				int cid = cursor.getInt(cursor
						.getColumnIndexOrThrow(ReminderDbAdapter.KEY_CID));
				String date = cursor.getString(cursor
						.getColumnIndexOrThrow(ReminderDbAdapter.KEY_DATE));
				try {
					Reminder rem = new Reminder(MovieService.getMovie(mid),
							CinemaService.getCinema(cid), date);
					list.add(rem);
				} catch (Exception e) {
					Log.e("InternalReminderRepository", "Exception thrown.");
				}
			}
		}
	}

	public ArrayList<Reminder> getReminders() {
		return list;
	}

	public void addReminder(Reminder reminder) {
		helper.createReminder(reminder);
	}
}
