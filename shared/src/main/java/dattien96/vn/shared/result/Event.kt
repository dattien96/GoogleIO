package dattien96.vn.shared.result

import androidx.lifecycle.Observer

/**
 * Class chứa LiveData đại diện cho 1 event
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandle = false
        private set //bên ngoài chỉ read không write

    /**
     * Trả về content lần đầu
     * Lần sau thì đã gán lại check, sẽ return null
     * Cái này tương tự SingleLiveEvent custom
     * Chỉ lần đầu mới trigger event, nwhungx lần sau null thì k trigger lại nữa
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandle) {
            null
        } else {
            hasBeenHandle = true
            content
        }
    }

    /**
     * Trả về content kể cả nó đã đc trigger rồi (có thể duplicate event )
     */
    fun peekContent(): T = content
}

/**
 * Ob của Event. Nhận vào lambda action
 * Khi ob change, nếu event đã được triger thì sẽ k làm gì ( fun getContentIfNotHandled return null)
 * Còn nếu event chưa đc triger ( check = false ) thì fun return content và action lambde đc exe
 */
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }
}
