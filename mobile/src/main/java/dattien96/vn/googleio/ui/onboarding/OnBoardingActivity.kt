package dattien96.vn.googleio.ui.onboarding

import android.graphics.Color
import android.os.Bundle
import android.view.View
import dagger.android.support.DaggerAppCompatActivity
import dattien96.vn.googleio.R
import dattien96.vn.shared.util.inTransaction

class OnBoardingActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        window.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        if (savedInstanceState == null) {
            supportFragmentManager.inTransaction {
                add(R.id.fragment_container, OnBoardingFragment())
            }
        }
    }
}
