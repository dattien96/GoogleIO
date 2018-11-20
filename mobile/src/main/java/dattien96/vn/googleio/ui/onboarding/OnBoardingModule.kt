package dattien96.vn.googleio.ui.onboarding

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import dattien96.vn.shared.di.FragmentScoped
import dattien96.vn.shared.di.ViewModelKey

@Module
abstract class OnBoardingModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeOnboardingFragment(): OnBoardingFragment

    @Binds
    @IntoMap
    @ViewModelKey(OnBoardingViewModel::class)
    internal abstract fun bindOnboardingViewModel(viewModel: OnBoardingViewModel): ViewModel
}
