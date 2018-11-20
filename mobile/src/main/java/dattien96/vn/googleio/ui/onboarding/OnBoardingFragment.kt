package dattien96.vn.googleio.ui.onboarding

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.google.samples.apps.iosched.ui.onboarding.CountdownFragment
import com.google.samples.apps.iosched.ui.onboarding.CustomizeScheduleFragment
import com.google.samples.apps.iosched.ui.onboarding.WelcomeFragment
import dagger.android.support.DaggerFragment
import dattien96.vn.googleio.databinding.FragmentOnboardingBinding
import dattien96.vn.googleio.ui.MainActivity
import dattien96.vn.shared.result.EventObserver
import dattien96.vn.shared.util.TimeUtils
import dattien96.vn.shared.util.viewModelProvider
import javax.inject.Inject

class OnBoardingFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var onboardingViewModel: OnBoardingViewModel

    private lateinit var binding: FragmentOnboardingBinding

    private lateinit var pagerPager: ViewPagerPager

    private val handler = Handler()

    // Auto-advance the view pager to give overview of app benefits
    private val advancePager: Runnable = object : Runnable {
        override fun run() {
            pagerPager.advance()
            handler.postDelayed(this, AUTO_ADVANCE_DELAY)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onboardingViewModel = viewModelProvider(viewModelFactory)

        binding = FragmentOnboardingBinding.inflate(inflater, container, false).apply {
            viewModel = onboardingViewModel
            setLifecycleOwner(this@OnBoardingFragment)
            pager.adapter = OnboardingAdapter(childFragmentManager)
            pagerPager = ViewPagerPager(pager)
            // If user touches pager then stop auto advance
            pager.setOnTouchListener { _, _ ->
                handler.removeCallbacks(advancePager)
                false
            }
        }

        onboardingViewModel.navigateToMainActivity.observe(this, EventObserver {
            requireActivity().run {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })

        return binding.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        handler.postDelayed(advancePager, INITIAL_ADVANCE_DELAY)
    }

    override fun onDetach() {
        handler.removeCallbacks(advancePager)
        super.onDetach()
    }

    companion object {
        private const val AUTO_ADVANCE_DELAY = 6_000L
        private const val INITIAL_ADVANCE_DELAY = 3_000L
    }
}

class OnboardingAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    // Don't show then countdown fragment if the conference has already started
    private val fragments = if (TimeUtils.conferenceHasStarted()) {
        arrayOf(
            WelcomeFragment(),
            CustomizeScheduleFragment()
        )
    } else {
        arrayOf(
            WelcomeFragment(),
            CustomizeScheduleFragment(),
            CountdownFragment()
        )
    }

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size
}