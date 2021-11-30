package com.andreyyurko.firstapp.ui.signin

import androidx.lifecycle.viewModelScope
import com.andreyyurko.firstapp.repository.AuthRepository
import com.andreyyurko.firstapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SignInViewModel : BaseViewModel() {

    fun signIn(email: String, password : String) {
        viewModelScope.launch {
            AuthRepository.signIn(email, password)
        }
    }
}