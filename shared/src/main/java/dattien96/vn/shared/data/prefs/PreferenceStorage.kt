package dattien96.vn.shared.data.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface PreferenceStorage {
    var onBoardingCompleted: Boolean
}

class SharedPreferenceStorage @Inject constructor(context: Context): PreferenceStorage {

    private val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME,
        Context.MODE_PRIVATE
    )
    
    override var onBoardingCompleted by BooleanPreference(prefs, PREF_ONBOARDING, false)

    companion object {
        const val PREFS_NAME = "googleio"
        const val PREF_ONBOARDING = "pref_onboarding"
    }
}

class BooleanPreference(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: Boolean
) : ReadWriteProperty<Any, Boolean> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        return preferences.getBoolean(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        preferences.edit { putBoolean(name, value) }
    }
}

class StringPreference(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: String?
) : ReadWriteProperty<Any, String?> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): String? {
        return preferences.getString(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
        preferences.edit { putString(name, value) }
    }
}