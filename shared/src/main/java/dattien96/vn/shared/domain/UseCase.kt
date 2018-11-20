package dattien96.vn.shared.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dattien96.vn.shared.domain.internal.DefaultScheduler
import dattien96.vn.shared.result.Result

abstract class UseCase<in P,R> {

    private val taskScheduler = DefaultScheduler

    /**
     * Exe UseCase và đưa [Result] vào 1 LiveData
     * Fun invoke operator gọi ngầm khi instance của class được init
     */
    operator fun invoke(params: P, result: MutableLiveData<Result<R>>) {
        try {
            taskScheduler.execute {
                try {
                    execute(params).let { useCaseResult ->
                        result.postValue(Result.Success(useCaseResult))
                    }
                } catch (e: Exception) {
                    result.postValue(Result.Error(e))
                }
            }
        } catch (e: Exception) {
            result.postValue(Result.Error(e))
        }
    }

    /**
     * Tương tự fun trên nhưng khai báo LiveData mặc định
     */
    operator fun invoke(parameters: P): LiveData<Result<R>> {
        val liveCallback: MutableLiveData<Result<R>> = MutableLiveData()
        this(parameters, liveCallback)
        return liveCallback
    }

    /** Executes usecase synchronously  */
    fun executeNow(parameters: P): Result<R> {
        return try {
            Result.Success(execute(parameters))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * UseCase execute fun
     * Override action ở class UseCase cụ thể
     */
    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: P): R
}
