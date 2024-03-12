package vn.tutorial.todolist

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.DebugLogger
import vn.tutorial.todolist.constants.CHANNEL_DESCRIPTION
import vn.tutorial.todolist.constants.CHANNEL_ID
import vn.tutorial.todolist.constants.CHANNEL_NAME
import vn.tutorial.todolist.data.Container
import vn.tutorial.todolist.data.DefaultContainer

class TodoApplication : Application() {
    lateinit var container: Container
    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer(this)

        val notificationChanel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = CHANNEL_DESCRIPTION
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChanel);
    }
}