package com.andreyyurko.firstapp.ui.profile

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.firstapp.R
import com.andreyyurko.firstapp.databinding.FragmentProfileBinding
import com.andreyyurko.firstapp.ui.base.BaseFragment

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    private val viewBinding by viewBinding(FragmentProfileBinding::bind)

    private val viewModel: ProfileViewModel by viewModels()
}