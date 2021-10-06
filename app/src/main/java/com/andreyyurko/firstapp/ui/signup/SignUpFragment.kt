package com.andreyyurko.firstapp

import androidx.fragment.app.viewModels
import com.andreyyurko.firstapp.databinding.FragmentSignUpBinding
import com.andreyyurko.firstapp.ui.base.BaseFragment
import com.andreyyurko.firstapp.ui.signin.SignInViewModel

class SignUnFragment : BaseFragment(R.layout.fragment_sign_up) {

    //private val viewBinding by viewBinding(FragmentSignUpBinding::bind)

    private val viewModel: SignInViewModel by viewModels()
}