package dattien96.vn.googleio.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dattien96.vn.shared.domain.prefs.OnboardingCompletedUseCase
import dattien96.vn.shared.result.Event
import dattien96.vn.shared.result.Result
import dattien96.vn.shared.util.map
import javax.inject.Inject

class LaunchViewModel @Inject constructor(
    onboardingCompletedUseCase: OnboardingCompletedUseCase
) : ViewModel() {

    private val onboardingCompletedResult = MutableLiveData<Result<Boolean>>()
    val launchDestination: LiveData<Event<LaunchDestination>>

    init {

        //check onboarding by usecase invoke operator fun
        onboardingCompletedUseCase(Unit, onboardingCompletedResult)

        launchDestination = onboardingCompletedResult.map {
            // If this check fails, prefer to launch main activity than show onboarding too often
            if ((it as? Result.Success)?.data == false) {
                Event(LaunchDestination.ON_BOARDING)
            } else {
                Event(LaunchDestination.MAIN_ACTIVITY)
            }
        }
    }
}

enum class LaunchDestination {
    ON_BOARDING,
    MAIN_ACTIVITY
}
