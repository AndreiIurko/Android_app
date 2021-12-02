package com.andreyyurko.firstapp.ui


import com.andreyyurko.firstapp.repository.OldAuthRepository
import com.andreyyurko.firstapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow


class MainViewModel : BaseViewModel() {
    val isAuthorizedFlow: Flow<Boolean> = OldAuthRepository.isAuthorizedFlow
}