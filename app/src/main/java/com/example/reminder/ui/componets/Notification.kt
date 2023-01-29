package com.example.reminder.ui.componets

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat.*
import androidx.core.app.NotificationCompat.*
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.example.reminder.*
import com.example.reminder.R
import com.example.reminder.data.Note
import java.util.*
import java.util.concurrent.TimeUnit


private const val CHANNEL_ID = "wow007"
//private const val ACTION_SNOOZE = "SNOOZE"

class ReminderWorker(private val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        createNotification(
            context,
            inputData.getString("note").toString(),
            inputData.getInt("id", 0)
        )
        return Result.success()
    }
}

fun createWorkRequest(context: Context, note: Note): UUID {
//    val boomDelay = note.dateTime.toInstant(ZoneOffset.UTC).epochSecond -
//            LocalDateTime.now().toInstant(ZoneOffset.UTC).epochSecond

    val reminder = OneTimeWorkRequestBuilder<ReminderWorker>()
        .setInitialDelay(note.delay, TimeUnit.SECONDS)
        .setInputData(workDataOf("note" to note.note, "id" to note.id))
        .addTag("Reminder").build()

    WorkManager.getInstance(context).enqueue(reminder)
    return reminder.id
}

@SuppressLint("MissingPermission")
private fun createNotification(context: Context, note: String, id: Int) {
    createNotificationChannel(context)

    val defaultIntent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    }
    val defaultPendingIntent: PendingIntent = PendingIntent.getActivity(
        context, id, defaultIntent, PendingIntent.FLAG_IMMUTABLE
    )

    val fullScreenIntent = Intent(context, NotificationActivity::class.java)
    val fullScreenPendingIntent = PendingIntent.getActivity(
        context, id, fullScreenIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val dismissIntent =
        Intent(context, NotificationReceiver::class.java).apply {
            action = ACTION_DISMISS
            putExtra(EXTRA_NOTIFICATION_ID, id)
        }
    val dismissPendingIntent: PendingIntent = PendingIntent.getBroadcast(
        context, id, dismissIntent,
        PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val builder = Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground).setContentTitle(note).setAutoCancel(true)
        .setPriority(PRIORITY_MAX).setContentIntent(defaultPendingIntent).setOngoing(true)
        .setDefaults(Notification.DEFAULT_ALL).setFullScreenIntent(fullScreenPendingIntent, true)
        .setVisibility(VISIBILITY_PUBLIC).setCategory(CATEGORY_REMINDER)
        .addAction(R.drawable.ic_launcher_foreground, ACTION_DISMISS, dismissPendingIntent)
//        .addAction(R.drawable.ic_launcher_foreground, ACTION_SNOOZE, defaultPendingIntent)
//        .addAction(R.drawable.ic_launcher_foreground, ACTION_CALL, defaultPendingIntent)

    NotificationManagerCompat.from(context).notify(id, builder.build())
}

private fun createNotificationChannel(context: Context) {

    val name = "Reminder"
    val descriptionText = "Reminds your of your scheduled activities"
    val importance = NotificationManager.IMPORTANCE_HIGH
    val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
        description = descriptionText
    }

    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}


/*
@Composable
fun ShowNotification(title: String, content: String) {
    val context = LocalContext.current
    val channelId = "wow007"
//    val notificationId = 0

    LaunchedEffect(Unit) {
        createNotificationChannel(channelId, context)
    }

    val reminder = OneTimeWorkRequestBuilder<ReminderWorker>().setInitialDelay(15, TimeUnit.SECONDS)
        .addTag("Reminder").build()

    WorkManager.getInstance(context).enqueue(reminder)
}

// shows a simple notification with a tap action to show an activity

fun showSimpleNotificationWithTapAction(
    context: Context,
    channelId: String,
    notificationId: Int,
    textTitle: String,
    textContent: String,
    priority: Int = NotificationCompat.PRIORITY_MAX
) {

    val intent = Intent(context, ReminderApplication::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent: PendingIntent =
        PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

    val snoozeIntent = Intent(context, ReminderApplication::class.java).apply {
        action = "snooze"
        putExtra(EXTRA_NOTIFICATION_ID, 0)
    }
    val snoozePendingIntent: PendingIntent =
        PendingIntent.getBroadcast(context, 0, snoozeIntent, PendingIntent.FLAG_IMMUTABLE)


    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(textTitle).setContentText(textContent)
        .addAction(R.drawable.ic_launcher_foreground, "Snooze", snoozePendingIntent)
        .setPriority(priority).setContentIntent(pendingIntent).setAutoCancel(true)


    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(
                context as Activity, arrayOf("Notification Permission"), 0
            )
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notify(notificationId, builder.build())
    }
}
*/