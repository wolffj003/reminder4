package com.example.examplereminderlevel5.db

import android.content.Context
import com.example.examplereminderlevel5.model.Reminder

class ReminderRepository(context: Context) {

    private var reminderDao: ReminderDao

    init {
        val reminderRoomDB = ReminderRoomDB.getDatabase(context)
        reminderDao = reminderRoomDB!!.reminderDao()
    }

    suspend fun getAllReminders(): List<Reminder> {
        return reminderDao.getAllReminders()
    }

    suspend fun deleteAllReminders() {
        return reminderDao.deleteAllReminders()
    }

    suspend fun insertReminder(reminder: Reminder) {
        reminderDao.insertReminder(reminder)
    }

    suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.deleteReminder(reminder)
    }
}
