package com.utcn.watchwithme.services;

import java.util.ArrayList;

import com.utcn.watchwithme.objects.Reminder;

public class AgendaService {

	private static ArrayList<Reminder> reminderList = new ArrayList<Reminder>();

	public static void add(Reminder reminder) {
		reminderList.add(reminder);
	}

	public static ArrayList<Reminder> getReminders() {
		return reminderList;
	}
}
