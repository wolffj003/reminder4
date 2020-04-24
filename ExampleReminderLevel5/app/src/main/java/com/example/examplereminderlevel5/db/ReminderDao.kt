package com.example.examplereminderlevel5.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.examplereminderlevel5.model.Reminder

@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminderTable")
    fun getAllReminders(): LiveData<List<Reminder>>

    @Query("DELETE FROM reminderTable")
    fun deleteAllReminders()

    @Insert
    suspend fun insertReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)
}
