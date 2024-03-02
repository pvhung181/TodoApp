package vn.tutorial.todolist.data.repository

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import vn.tutorial.todolist.constants.NOTIFICATION_CONTENT
import vn.tutorial.todolist.workers.TaskNotificationWorker
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

class WorManagerNotificationRepository(context: Context) {

    private val workManager: WorkManager = WorkManager.getInstance(context)

    fun setWorkRequest(content: String, start: LocalDateTime) {
        val time:Long = LocalDateTime.now().until(start, ChronoUnit.SECONDS);
        val request = OneTimeWorkRequestBuilder<TaskNotificationWorker>()
            .setInitialDelay(time, TimeUnit.SECONDS)
            .setInputData(createInputDataForNotification(content))
            .build()
        workManager.enqueue(request)
    }

    fun cancelWorkRequest() {

    }

    private fun createInputDataForNotification(content: String): Data {
        val builder = Data.Builder()
        builder.putString(NOTIFICATION_CONTENT, content)
        return builder.build()
    }
}