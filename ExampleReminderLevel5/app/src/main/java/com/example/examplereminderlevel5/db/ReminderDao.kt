package com.example.examplereminderlevel5.db

import androidx.room.*
import com.example.examplereminderlevel5.model.Reminder

@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminderTable")
    suspend fun getAllReminders(): List<Reminder>

    @Query("DELETE FROM reminderTable")
    suspend fun deleteAllReminders()

    @Insert
    suspend fun insertReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    @Update
    suspend fun updateReminder(reminder: Reminder)
}
