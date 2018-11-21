package dattien96.vn.googleio.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import dattien96.vn.googleio.R
import dattien96.vn.googleio.ui.infor.InforFragment
import dattien96.vn.googleio.ui.map.MapFragment
import dattien96.vn.googleio.ui.schedule.ScheduleFragment
import dattien96.vn.googleio.ui.schedule.ScheduleViewModel
import dattien96.vn.shared.util.inTransaction
import dattien96.vn.shared.util.viewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var currentFragment: MainNavigationFragment

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var scheduleViewModel: ScheduleViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Lát ở bên ScheduleFragment cũng lấy ra cùng 1 thể hiện với act này
        scheduleViewModel = viewModelProvider(viewModelFactory)

        navigation.apply {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_schedule -> {
                        replaceFragment(ScheduleFragment())
                        true
                    }
                    R.id.navigation_map -> {
                        // Scroll to current event next time the schedule is opened.
                        replaceFragment(MapFragment())
                        true
                    }
                    R.id.navigation_info -> {
                        // Scroll to current event next time the schedule is opened.
                        replaceFragment(InforFragment())
                        true
                    }
                    else -> false
                }
            }

            //do nothing when reselect item
            setOnNavigationItemReselectedListener { }

            if (savedInstanceState == null) {
                // Show Schedule on first creation
                navigation.selectedItemId = R.id.navigation_schedule
            } else {
                // Find the current fragment
                currentFragment =
                        supportFragmentManager.findFragmentById(FRAGMENT_ID) as? MainNavigationFragment
                        ?: throw IllegalStateException("Activity recreated, but no fragment found!")
            }
        }
    }

    private fun <F> replaceFragment(fragment: F) where F : Fragment, F : MainNavigationFragment {
        supportFragmentManager.inTransaction {
            currentFragment = fragment
            replace(FRAGMENT_ID, fragment)
        }
    }

    override fun onBackPressed() {
        if (!currentFragment.onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        currentFragment.onUserInteraction()
    }

    companion object {
        private const val FRAGMENT_ID = R.id.fragment_container
    }
}
