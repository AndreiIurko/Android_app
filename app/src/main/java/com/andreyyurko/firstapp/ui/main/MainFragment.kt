package com.andreyyurko.firstapp.ui.main

import androidx.fragment.app.viewModels
import com.andreyyurko.firstapp.ui.base.BaseFragment
import com.andreyyurko.firstapp.R
import com.andreyyurko.firstapp.databinding.FragmentMainBinding
import com.andreyyurko.firstapp.ui.signin.SignInViewModel

class MainFragment : BaseFragment(R.layout.fragment_main) {
    private val viewBinding by viewBinding(FragmentMainBinding::bind)

    private val viewModel: SignInViewModel by viewModels()
}