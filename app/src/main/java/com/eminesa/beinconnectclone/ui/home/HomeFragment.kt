package com.eminesa.beinconnectclone.ui.home

import androidx.fragment.app.viewModels
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

    private val viewModel: HomeViewModel by viewModels()

    override fun FragmentHomeBinding.bindScreen() {

        // Başlığı değiştirme
        customToolbar.setTitle("Film")

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
                    tab.text = "Yabancı Film"
                    tab.view.setBackgroundResource(R.drawable.tab_selected_background)
                }

                1 -> tab.text = "Yerli Film"
                2 -> tab.text = "Betimleme"
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