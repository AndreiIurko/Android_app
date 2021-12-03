package com.andreyyurko.firstapp.ui.bookmarks

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.firstapp.R
import com.andreyyurko.firstapp.databinding.FragmentBookmarksBinding
import com.andreyyurko.firstapp.ui.base.BaseFragment

class BookmarksFragment : BaseFragment(R.layout.fragment_bookmarks) {
    private val viewBinding by viewBinding(FragmentBookmarksBinding::bind)

    private val viewModel: BookmarksViewModel by viewModels()
}