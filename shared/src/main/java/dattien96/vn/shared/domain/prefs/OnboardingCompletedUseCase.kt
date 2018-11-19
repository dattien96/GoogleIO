package dattien96.vn.shared.domain.prefs

import dattien96.vn.shared.data.prefs.PreferenceStorage
import dattien96.vn.shared.domain.UseCase
import javax.inject.Inject

open class OnboardingCompletedUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage
): UseCase<Unit, Boolean>() {
    override fun execute(parameters: Unit): Boolean = preferenceStorage.onBoardingCompleted
}
