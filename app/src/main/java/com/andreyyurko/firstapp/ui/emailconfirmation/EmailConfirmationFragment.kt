package com.andreyyurko.firstapp.ui.emailconfirmation

import androidx.fragment.app.viewModels
import com.andreyyurko.firstapp.ui.base.BaseFragment
import com.andreyyurko.firstapp.R
import com.andreyyurko.firstapp.ui.signin.SignInViewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.firstapp.databinding.FragmentEmailConfirmationBinding

class EmailConfirmationFragment : BaseFragment(R.layout.fragment_email_confirmation) {
    private val viewBinding by viewBinding(FragmentEmailConfirmationBinding::bind)


    private val viewModel: SignInViewModel by viewModels()
}