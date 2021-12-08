package com.andreyyurko.firstapp.ui.profile

import androidx.lifecycle.viewModelScope
import com.andreyyurko.firstapp.entity.ProfileInformation
import com.andreyyurko.firstapp.interactor.AuthInteractor
import com.andreyyurko.firstapp.interactor.ProfileInformationInteractor
import com.andreyyurko.firstapp.ui.base.BaseViewModel
import com.andreyyurko.firstapp.ui.signin.SignInViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val profileInformationInteractor: ProfileInformationInteractor
) : BaseViewModel() {
    private val profileInformationFlow = MutableStateFlow<ProfileInformation?>(null)

    fun getProfileInformation() {
        viewModelScope.launch {
            profileInformationFlow.emit(profileInformationInteractor.getProfileInformation().first())
        }
    }

    fun profileInformationStateFlow(): Flow<ProfileInformation?> {
        return profileInformationFlow.asStateFlow()
    }

    fun logout() {
        viewModelScope.launch {
            authInteractor.logout()
        }
    }
}