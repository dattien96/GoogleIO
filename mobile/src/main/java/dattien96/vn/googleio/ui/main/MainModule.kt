package dattien96.vn.googleio.ui.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import dattien96.vn.googleio.ui.infor.InfoModule
import dattien96.vn.googleio.ui.infor.InforFragment
import dattien96.vn.googleio.ui.map.MapFragment
import dattien96.vn.googleio.ui.map.MapModule
import dattien96.vn.googleio.ui.schedule.ScheduleFragment
import dattien96.vn.googleio.ui.schedule.ScheduleModule
import dattien96.vn.googleio.ui.schedule.ScheduleViewModel
import dattien96.vn.shared.di.FragmentScoped
import dattien96.vn.shared.di.ViewModelKey

@Module
abstract class MainModule {
    @FragmentScoped
    @ContributesAndroidInjector(modules = [ScheduleModule::class])
    internal abstract fun contributeScheduleFragment(): ScheduleFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [MapModule::class])
    internal abstract fun contributeMapFragment(): MapFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [InfoModule::class])
    internal abstract fun contributeInforFragment(): InforFragment

    /**
     * Bình thường thì ViewModel của frg này sẽ đc provide trong module cụ thể của fragment. Tuy
     * nhiên do MainAct cũng cần truy cập tới Vm này nên ta sẽ provide ở đây. Đương nhiên ở frg
     * cũng là lấy ra cùng thể hiện vm mà act đã lấy trước đó (shared viewmodel)
     */
    @Binds
    @IntoMap
    @ViewModelKey(ScheduleViewModel::class)
    abstract fun bindScheduleViewModel(viewModel: ScheduleViewModel): ViewModel
}
