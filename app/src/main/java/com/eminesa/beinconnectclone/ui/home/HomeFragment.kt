package com.eminesa.beinconnectclone.ui.home

import com.eminesa.beinconnectclone.R
import com.eminesa.beinconnectclone.common.DemoCollectionAdapter
import com.eminesa.beinconnectclone.databinding.FragmentHomeBinding
import com.eminesa.beinconnectclone.ui.base.BaseFragment
import com.eminesa.beinconnectclone.ui.descriptive.DescriptiveMovieFragment
import com.eminesa.beinconnectclone.ui.domestic.DomesticMovieFragment
import com.eminesa.beinconnectclone.ui.foreign.ForeignMovieFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun FragmentHomeBinding.bindScreen() {

        // Başlığı değiştirme
        customToolbar.setTitle(getString(R.string.movie))

        //viewPager
        val fragments = listOf(
            ForeignMovieFragment(),
            DomesticMovieFragment(),
            DescriptiveMovieFragment()
        )

        val adapter = DemoCollectionAdapter(this@HomeFragment, fragments)
        viewPager.adapter = adapter

        // TabLayout ve ViewPager2'yi bağlama
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.foreign_film)
                    tab.view.setBackgroundResource(R.drawable.tab_selected_background)
                }
                1 -> tab.text = getString(R.string.domestic_film)
                2 -> tab.text = getString(R.string.descriptive)
                else -> tab.text = ""
            }

        }.attach()

        // Seçili sekmenin arka planını özelleştirme
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.view.setBackgroundResource(R.drawable.tab_selected_background)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.view.setBackgroundResource(R.drawable.tab_unselected_background)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }

}