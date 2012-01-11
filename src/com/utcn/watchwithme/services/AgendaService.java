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
			for (int i = 0; i < reminderList.size(); i++)
				for (int j = i + 1; j < reminderList.size(); j++) {
					Reminder ri = reminderList.get(i);
					Reminder rj = reminderList.get(j);
					if (ri.getDate().compareTo(rj.getDate()) < 0) {
						reminderList.set(i, rj);
						reminderList.set(j, ri);
					}
				}
		}
		return reminderList;
	}

	public static void deleteAll() {
		reminderList.clear();
		InternalReminderRepository.getInstance().deleteAll();
	}
}
