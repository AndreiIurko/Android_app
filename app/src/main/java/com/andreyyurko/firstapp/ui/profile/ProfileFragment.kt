package com.andreyyurko.firstapp.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.firstapp.R
import com.andreyyurko.firstapp.databinding.FragmentProfileBinding
import com.andreyyurko.firstapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    private val viewBinding by viewBinding(FragmentProfileBinding::bind)

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.logOutButton.applyInsetter {
            type(statusBars = true) { margin() }
        }

        viewBinding.logOutButton.setOnClickListener {
            viewModel.logout()
        }
        viewModel.getProfileInformation()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileInformationStateFlow().collect { info ->
                    if (info != null) {
                        viewBinding.emailTextView.text = info.email
                        viewBinding.firstNameTextView.text = info.firstName
                        viewBinding.lastNameTextView.text = info.lastName
                        viewBinding.nickNameTextView.text = info.nickName
                    }
                }
            }
        }

    }


}