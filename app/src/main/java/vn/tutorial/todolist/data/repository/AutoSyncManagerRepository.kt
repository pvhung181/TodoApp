package vn.tutorial.todolist.data.repository

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import vn.tutorial.todolist.constants.TAG_PERIODIC_SYNC
import vn.tutorial.todolist.workers.AutoSyncTaskWorker
import java.util.Calendar
import java.util.concurrent.TimeUnit

class AutoSyncManagerRepository(ctx: Context) {
    private val workManager: WorkManager = WorkManager.getInstance(ctx)

    fun setWorkRequest() {
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()

        dueDate.set(Calendar.HOUR_OF_DAY, 0)
        dueDate.set(Calendar.MINUTE, 0)
        dueDate.set(Calendar.SECOND, 0)
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }
        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
        val dailyWorkRequest = OneTimeWorkRequestBuilder<AutoSyncTaskWorker>()
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .addTag(TAG_PERIODIC_SYNC)
            .build()
        workManager.enqueue(dailyWorkRequest)
    }
}