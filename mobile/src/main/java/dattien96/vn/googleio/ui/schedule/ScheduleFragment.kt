package dattien96.vn.googleio.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import dattien96.vn.googleio.R
import dattien96.vn.googleio.databinding.FragmentScheduleBinding
import dattien96.vn.googleio.ui.main.MainNavigationFragment
import dattien96.vn.shared.util.activityViewModelProvider
import javax.inject.Inject

class ScheduleFragment: DaggerFragment(), MainNavigationFragment {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var scheduleViewModel: ScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //lấy ra instance của vm (cùng thể hiện với act đã lấy trước đó)
        scheduleViewModel = activityViewModelProvider(viewModelFactory)

        val binding = FragmentScheduleBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@ScheduleFragment)
            viewModel = this@ScheduleFragment.scheduleViewModel
        }

        return binding.root
    }
}
