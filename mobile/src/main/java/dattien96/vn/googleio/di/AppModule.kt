package dattien96.vn.googleio.di

import android.content.ClipboardManager
import android.content.Context
import android.net.wifi.WifiManager
import dagger.Module
import dagger.Provides
import dattien96.vn.googleio.MainApplication

/**
 * Provide các class cần dùng trong app scope
 */
@Module
class AppModule {

    @Provides
    fun provideContext(application: MainApplication): Context {
        return application.applicationContext
    }

    @Provides
    fun providesWifiManager(context: Context): WifiManager =
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    @Provides
    fun providesClipboardManager(context: Context): ClipboardManager =
        context.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE)
                as ClipboardManager

}
