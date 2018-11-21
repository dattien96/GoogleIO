package dattien96.vn.googleio.ui.infor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dattien96.vn.googleio.R
import dattien96.vn.googleio.ui.main.MainNavigationFragment

class InforFragment: Fragment(), MainNavigationFragment {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_infor, container, false)
}
