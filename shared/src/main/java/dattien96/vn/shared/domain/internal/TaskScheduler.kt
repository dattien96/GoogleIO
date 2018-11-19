package dattien96.vn.shared.domain.internal

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private const val NUMBER_OF_THREADS = 4 // số thread custom cho 1 execute pool, phụ thuộc device


interface Scheduler {

    fun execute(task: () -> Unit)

    fun postToMainThread(task: () -> Unit)

    fun postDelayedToMainThread(delay: Long, task: () -> Unit)
}

object DefaultScheduler: Scheduler {
    private var delegate: Scheduler = AsyncScheduler

    /**
     * Sets the new delegate scheduler, null to revert to the default async one.
     */
    fun setDelegate(newDelegate: Scheduler?) {
        delegate = newDelegate ?: AsyncScheduler
    }

    override fun execute(task: () -> Unit) {
        delegate.execute(task)
    }

    override fun postToMainThread(task: () -> Unit) {
        delegate.postToMainThread(task)
    }

    override fun postDelayedToMainThread(delay: Long, task: () -> Unit) {
        delegate.postDelayedToMainThread(delay, task)
    }
}

/**
 * Runs tasks trong [ExecutorService] với sô lượng thread của 1 pool đc fix
 */
internal object AsyncScheduler : Scheduler {

    private val executorService: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

    override fun execute(task: () -> Unit) {
        executorService.execute(task)
    }

    override fun postToMainThread(task: () -> Unit) {
        if (isMainThread()) {
            task()
        } else {
            val mainThreadHandler = Handler(Looper.getMainLooper())
            mainThreadHandler.post(task)
        }
    }

    override fun postDelayedToMainThread(delay: Long, task: () -> Unit) {
        val mainThreadHandler = Handler(Looper.getMainLooper())
        mainThreadHandler.postDelayed(task, delay)
    }

    private fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread === Thread.currentThread()
    }
}

object SyncScheduler : Scheduler {
    private val postDelayedTasks = mutableListOf<() -> Unit>()

    override fun execute(task: () -> Unit) {
        task()
    }

    override fun postToMainThread(task: () -> Unit) {
        task()
    }

    override fun postDelayedToMainThread(delay: Long, task: () -> Unit) {
        postDelayedTasks.add(task)
    }

    fun runAllScheduledPostDelayedTasks() {
        val tasks = postDelayedTasks.toList()
        clearScheduledPostdelayedTasks()
        for (task in tasks) {
            task()
        }
    }

    fun clearScheduledPostdelayedTasks() {
        postDelayedTasks.clear()
    }
}
