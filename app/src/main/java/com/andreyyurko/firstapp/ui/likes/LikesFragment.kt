package com.andreyyurko.firstapp.ui.likes

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.firstapp.R
import com.andreyyurko.firstapp.databinding.FragmentLikesBinding
import com.andreyyurko.firstapp.ui.base.BaseFragment

class LikesFragment : BaseFragment(R.layout.fragment_likes) {
    private val viewBinding by viewBinding(FragmentLikesBinding::bind)

    private val viewModel: LikesViewModel by viewModels()
}