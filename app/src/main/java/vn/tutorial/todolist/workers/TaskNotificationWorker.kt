package vn.tutorial.todolist.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import vn.tutorial.todolist.constants.NOTIFICATION_CONTENT
import vn.tutorial.todolist.service.NotificationService

class TaskNotificationWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    private val notificationService: NotificationService = NotificationService(context)
    override suspend fun doWork(): Result {
        return try {
            val content = inputData.getString(NOTIFICATION_CONTENT)
            notificationService.showBasicNotification(content!!)
            Result.success()
        }catch (ex: NullPointerException) {
            Result.failure()
        }
    }

}