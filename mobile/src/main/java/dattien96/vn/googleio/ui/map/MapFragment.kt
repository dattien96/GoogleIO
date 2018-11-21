package dattien96.vn.googleio.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dattien96.vn.googleio.R
import dattien96.vn.googleio.ui.main.MainNavigationFragment

class MapFragment: Fragment(), MainNavigationFragment {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_map, container, false)
}
