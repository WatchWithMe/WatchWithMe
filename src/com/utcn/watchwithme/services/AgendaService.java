package com.utcn.watchwithme.services;

import java.util.ArrayList;

import com.utcn.watchwithme.objects.Reminder;
import com.utcn.watchwithme.repository.InternalReminderRepository;

/**
 * 
 * @author Vlad
 * 
 */
public class AgendaService {

	private static ArrayList<Reminder> reminderList = new ArrayList<Reminder>();

	public static void add(Reminder reminder) {
		reminderList.add(reminder);
		InternalReminderRepository.getInstance().addReminder(reminder);
	}

	public static ArrayList<Reminder> getReminders() {
		if (reminderList.size() == 0) {
			reminderList = InternalReminderRepository.getInstance()
					.getReminders();
		}
		return reminderList;
	}
}
