package dattien96.vn.shared.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*

/**
 * ViewModel extension
 */

inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(this, provider).get(VM::class.java)

inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(this, provider).get(VM::class.java)

// get activity viewModel from frg
inline fun <reified VM : ViewModel> Fragment.activityViewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(requireActivity(), provider).get(VM::class.java)

// get parent frg viewModel from frg
inline fun <reified VM : ViewModel> Fragment.parentViewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(parentFragment!!, provider).get(VM::class.java)


/**
 * LiveData extension
 */

fun <X, Y> LiveData<X>.map(body: (X) -> Y): LiveData<Y> {
    return Transformations.map(this, body)
}

fun <X, Y> LiveData<X>.switchMap(body: (X) -> LiveData<Y>): LiveData<Y> {
    return Transformations.switchMap(this, body)
}
