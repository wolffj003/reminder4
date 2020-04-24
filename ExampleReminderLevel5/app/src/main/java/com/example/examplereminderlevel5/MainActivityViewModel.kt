package com.example.examplereminderlevel5

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.examplereminderlevel5.db.ReminderRepository
import com.example.examplereminderlevel5.model.Reminder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val reminderRepository = ReminderRepository(application.applicationContext)
    val reminders: LiveData<List<Reminder>> = reminderRepository.getAllReminders()

    fun insertReminder(reminder: Reminder) {
        ioScope.launch { reminderRepository.insertReminder(reminder) }
    }

    fun deleteReminder(reminder: Reminder) {
        ioScope.launch { reminderRepository.deleteReminder(reminder) }
    }

    fun deleteAllReminders() {
        ioScope.launch { reminderRepository.deleteAllReminders() }
    }
}