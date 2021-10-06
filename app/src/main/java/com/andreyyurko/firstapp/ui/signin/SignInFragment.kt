package com.andreyyurko.firstapp.ui.signin

import androidx.fragment.app.viewModels
import com.andreyyurko.firstapp.ui.base.BaseFragment
import com.andreyyurko.firstapp.R
import com.andreyyurko.firstapp.databinding.FragmentSignInBinding

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    //private val viewBinding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel: SignInViewModel by viewModels()
}