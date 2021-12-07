package com.andreyyurko.firstapp.ui.profile

import androidx.lifecycle.viewModelScope
import com.andreyyurko.firstapp.interactor.AuthInteractor
import com.andreyyurko.firstapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authInteractor: AuthInteractor
) : BaseViewModel() {
    fun logout() {
        viewModelScope.launch {
            authInteractor.logout()
        }
    }
}