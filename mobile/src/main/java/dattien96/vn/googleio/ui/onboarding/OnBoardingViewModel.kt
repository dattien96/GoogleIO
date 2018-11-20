package dattien96.vn.googleio.ui.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dattien96.vn.shared.domain.prefs.OnboardingCompleteActionUseCase
import dattien96.vn.shared.result.Event
import javax.inject.Inject

class OnBoardingViewModel @Inject constructor(
    private val onboardingCompleteActionUseCase: OnboardingCompleteActionUseCase
): ViewModel() {
    val navigateToMainActivity = MutableLiveData<Event<Unit>>()

    fun getStartedClick() {
        onboardingCompleteActionUseCase(true)
        navigateToMainActivity.postValue(Event(Unit))
    }
}