package com.andreyyurko.firstapp.ui.signup

import androidx.fragment.app.viewModels
import com.andreyyurko.firstapp.R
import com.andreyyurko.firstapp.ui.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.firstapp.databinding.FragmentSignUpBinding
import com.andreyyurko.firstapp.ui.signin.SignInViewModel


class SignUnFragment : BaseFragment(R.layout.fragment_sign_up) {
    private val viewBinding by viewBinding(FragmentSignUpBinding::bind)

    private val viewModel: SignInViewModel by viewModels()
}