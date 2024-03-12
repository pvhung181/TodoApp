package vn.tutorial.todolist.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import vn.tutorial.todolist.constants.TAG_PERIODIC_SYNC
import vn.tutorial.todolist.data.TodoDatabase
import vn.tutorial.todolist.data.repository.TaskRepositoryImpl
import vn.tutorial.todolist.data.repository.UserRepositoryImpl
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.concurrent.TimeUnit

class AutoSyncTaskWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    private val userRepository = UserRepositoryImpl(TodoDatabase.getDatabase(ctx).getUserDao())
    private val taskRepositoryImpl = TaskRepositoryImpl(TodoDatabase.getDatabase(ctx).getTaskDao())
    private val workManager = WorkManager.getInstance(ctx)

    override suspend fun doWork(): Result {
        return try {
            val tasks = taskRepositoryImpl.getAllTasksNotTracking()
            val user = userRepository.getUserNotTracking(1)
            val completed = tasks.filter { task -> task.isCompleted }.size
            val currentDate = Calendar.getInstance()
            currentDate.add(Calendar.MINUTE, -1)
            val dueDate = Calendar.getInstance()

            userRepository.update(user.copy(
                totalTasks = tasks.size,
                comingTasks =  tasks.filter { task ->
                    task.dateBegin.isAfter(LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault()))
                }.size,
                completedTasks = completed
            ))

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

            Result.success()
        }catch (_: Exception) {
            Result.failure()
        }
    }
}