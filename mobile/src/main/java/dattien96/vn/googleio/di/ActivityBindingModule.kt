package dattien96.vn.googleio.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dattien96.vn.googleio.ui.launch.LaunchActivity
import dattien96.vn.googleio.ui.launch.LaunchModule
import dattien96.vn.shared.di.ActivityScoped

/**
 * Module cung cấp các module activity scope. Bình thường ta cần tạo subComponent cụ thể cho mỗi act
 * Nhưng với @Contribute, Dagger.Android sẽ auto tạo subCom này. Và Component cha của nó chính là
 * nơi mà [ActivityBindingModule] này đc khai báo - chính là AppComponent.
 * Không cần phải nói cho AppComponent biết về sự tồn tại của các SubCom này nữa ( như cách cũ là
 * cần khai báo và chỉ định tường minh ) .
 */
@Module
abstract class ActivityBindingModule {

    /**
     * internal cho phép fun đc truy cập trong module này
     * Việc dùng @Contribute  = (Tạo subComponent của Activity launch) + tạo fun inject() như cách cũ
     * Lưu ý kể cả Act không có thêm module con nào thì cũng phải @Contribute thì mới có thể dùng @Inject
     */
    @ActivityScoped
    @ContributesAndroidInjector(modules = [LaunchModule::class])
    internal abstract fun launchActivity(): LaunchActivity
}
