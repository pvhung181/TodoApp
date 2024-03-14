package vn.tutorial.todolist.service

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import okhttp3.internal.notify
import vn.tutorial.todolist.R
import vn.tutorial.todolist.constants.CHANNEL_ID
import vn.tutorial.todolist.constants.NOTIFICATION_TITLE
import kotlin.random.Random

class NotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun showBasicNotification(content: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(content)
            .setSmallIcon(R.drawable.calendar)
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }
}