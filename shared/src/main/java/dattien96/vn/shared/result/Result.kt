package dattien96.vn.shared.result

import java.lang.Exception

sealed class Result<out R> {
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T):Result<T>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * Var check successed cuar Result Instance.
 * Chỉ true khi nó là Type Success và data != null
 */
val Result<*>.successed
    get() = this is Result.Success && data != null
