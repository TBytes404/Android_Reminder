package com.example.reminder

import android.app.Application
import com.example.reminder.data.AccountRepository
import com.example.reminder.data.NotesRepository
import com.example.reminder.data.ReminderDatabase

class ReminderApplication : Application() {

    private val database: ReminderDatabase by lazy { ReminderDatabase.getDatabase(this) }
    val notesRepository: NotesRepository by lazy { NotesRepository(noteDao = database.noteDao()) }
    val accountRepository: AccountRepository by lazy { AccountRepository(this) }
}