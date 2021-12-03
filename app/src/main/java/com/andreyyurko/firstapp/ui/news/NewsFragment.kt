package com.andreyyurko.firstapp.ui.news

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.firstapp.R
import com.andreyyurko.firstapp.databinding.FragmentNewsBinding
import com.andreyyurko.firstapp.ui.base.BaseFragment


class NewsFragment : BaseFragment(R.layout.fragment_news) {
    private val viewBinding by viewBinding(FragmentNewsBinding::bind)

    private val viewModel: NewsViewModel by viewModels()
}