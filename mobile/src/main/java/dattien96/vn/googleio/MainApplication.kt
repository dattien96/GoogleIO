package dattien96.vn.googleio

import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dattien96.vn.googleio.di.DaggerAppComponent

class MainApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
            = DaggerAppComponent.builder().create(this)

    override fun onCreate() {
        super.onCreate()

        // ThreeTenBP for times and dates
        AndroidThreeTen.init(this)
    }
}
