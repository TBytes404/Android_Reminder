package com.example.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat.EXTRA_NOTIFICATION_ID
import androidx.core.app.NotificationManagerCompat
import com.example.reminder.data.NotesRepository
import com.example.reminder.data.ReminderDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


const val ACTION_DISMISS = "DISMISS"
//const val ACTION_SNOOZE = "SNOOZE"
//const val ACTION_CALL = "CALL"

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            val database: ReminderDatabase by lazy { ReminderDatabase.getDatabase(context.applicationContext) }
            val notesRepository: NotesRepository by lazy { NotesRepository(noteDao = database.noteDao()) }

            when (intent?.action) {
                ACTION_DISMISS -> {
                    val id = intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0)
                    CoroutineScope(Dispatchers.IO).launch { notesRepository.disableReminders(id) }
//                    Toast.makeText(context, "xxxix: $id", Toast.LENGTH_LONG).show()
                    NotificationManagerCompat.from(context).cancel(id)
                }
            }
        }
    }
}

/*
fun BroadcastReceiver.goAsync(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    val pendingResult = goAsync()
    CoroutineScope(SupervisorJob()).launch(context) {
        try {
            block()
        } finally {
            pendingResult.finish()
        }
    }
}
*/