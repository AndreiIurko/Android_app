package com.andreyyurko.firstapp.ui.likes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.firstapp.R
import com.andreyyurko.firstapp.databinding.FragmentLikesBinding
import com.andreyyurko.firstapp.ui.base.BaseFragment
import dev.chrisbanes.insetter.applyInsetter

class LikesFragment : BaseFragment(R.layout.fragment_likes) {
    private val viewBinding by viewBinding(FragmentLikesBinding::bind)

    private val viewModel: LikesViewModel by viewModels()

    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.titleTextView.applyInsetter {
            type(statusBars = true) { margin() }
        }
    }
}