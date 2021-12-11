package com.andreyyurko.firstapp.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.firstapp.R
import com.andreyyurko.firstapp.databinding.FragmentProfileBinding
import com.andreyyurko.firstapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter

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

    }


}