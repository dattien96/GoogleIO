package dattien96.vn.googleio

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dattien96.vn.googleio.di.DaggerAppComponent

class MainApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
            = DaggerAppComponent.builder().create(this)
}
