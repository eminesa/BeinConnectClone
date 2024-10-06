package com.eminesa.beinconnectclone.ui.home

import androidx.fragment.app.viewModels
import com.eminesa.beinconnectclone.databinding.FragmentHomeBinding
import com.eminesa.beinconnectclone.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun FragmentHomeBinding.bindScreen() {

       viewModel.getGenre()


       tvName.text = "Nav Ok"
    }

}