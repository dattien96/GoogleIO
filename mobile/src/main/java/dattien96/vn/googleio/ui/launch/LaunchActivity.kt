package dattien96.vn.googleio.ui.launch

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import dattien96.vn.googleio.ui.main.MainActivity
import dattien96.vn.googleio.ui.onboarding.OnBoardingActivity
import dattien96.vn.shared.result.EventObserver
import dattien96.vn.shared.util.viewModelProvider
import javax.inject.Inject

class LaunchActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: LaunchViewModel = viewModelProvider(viewModelFactory)
        viewModel.launchDestination.observe(this, EventObserver {
            when (it) {
                LaunchDestination.MAIN_ACTIVITY -> startActivity(Intent(this, MainActivity::class.java))
                LaunchDestination.ON_BOARDING -> startActivity(Intent(this, OnBoardingActivity::class.java))
            }
            finish()
        })
    }

}
