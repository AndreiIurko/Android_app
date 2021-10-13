package com.andreyyurko.firstapp.ui

import com.andreyyurko.firstapp.repository.AuthRepository
import com.andreyyurko.firstapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel : BaseViewModel() {
    val isAuthorizedFlow: Flow<Boolean> = AuthRepository.isAuthorizedFlow
}