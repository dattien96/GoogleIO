package dattien96.vn.googleio.di

import android.content.ClipboardManager
import android.content.Context
import android.net.wifi.WifiManager
import dagger.Module
import dagger.Provides
import dattien96.vn.googleio.MainApplication
import dattien96.vn.shared.data.prefs.PreferenceStorage
import dattien96.vn.shared.data.prefs.SharedPreferenceStorage
import javax.inject.Singleton

/**
 * Provide các class cần dùng trong app scope
 */
@Module
class AppModule {

    @Provides
    fun provideContext(application: MainApplication): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providesPreferenceStorage(context: Context): PreferenceStorage =
        SharedPreferenceStorage(context)

    @Provides
    fun providesWifiManager(context: Context): WifiManager =
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    @Provides
    fun providesClipboardManager(context: Context): ClipboardManager =
        context.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE)
                as ClipboardManager

}
