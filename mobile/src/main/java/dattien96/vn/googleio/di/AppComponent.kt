package dattien96.vn.googleio.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dattien96.vn.googleio.MainApplication
import dattien96.vn.shared.di.ViewModelModule
import javax.inject.Singleton

/**
 * Component chính của app. Được khởi tạo trong MainApplication
 *
 * Các module mới được khởi tạo và khai báo trong list module của component này
 * Ngoại trừ các module con đi theo activity được khai báo lúc contribute
 *
 * [AndroidSupportInjectionModule]: module default từ Dagger.Android có nhiệm vụ tạo và tự động init
 * các subcomponent ( với @Contribute )
 */

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class]
)
interface AppComponent : AndroidInjector<MainApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MainApplication>()
}
