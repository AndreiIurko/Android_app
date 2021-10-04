package com.andreyyurko.firstapp.ui.emailconfirmation

import androidx.fragment.app.viewModels
import com.andreyyurko.firstapp.ui.base.BaseFragment
import com.andreyyurko.firstapp.R
import com.andreyyurko.firstapp.databinding.FragmentEmailConfirmationBinding
import com.andreyyurko.firstapp.ui.signin.SignInViewModel

class EmailConfirmationFragment : BaseFragment(R.layout.fragment_email_confirmation) {
    private val viewBinding by viewBinding(FragmentEmailConfirmationBinding::bind)

    private val viewModel: SignInViewModel by viewModels()
}