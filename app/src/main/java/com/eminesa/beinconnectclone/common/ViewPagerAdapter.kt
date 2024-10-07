package com.eminesa.beinconnectclone.common

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DemoCollectionAdapter(
    fragment: Fragment, private val fragmentList: List<Fragment>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}
