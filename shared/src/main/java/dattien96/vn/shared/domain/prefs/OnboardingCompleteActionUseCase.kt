/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dattien96.vn.shared.domain.prefs

import dattien96.vn.shared.data.prefs.PreferenceStorage
import dattien96.vn.shared.domain.UseCase
import javax.inject.Inject

/**
 * Records whether onboarding has been completed.
 */
open class OnboardingCompleteActionUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<Boolean, Unit>() {
    override fun execute(completed: Boolean) {
        preferenceStorage.onBoardingCompleted = completed
    }
}