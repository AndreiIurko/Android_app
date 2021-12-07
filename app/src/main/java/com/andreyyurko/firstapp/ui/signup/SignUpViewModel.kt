package com.andreyyurko.firstapp.ui.signup

import androidx.lifecycle.viewModelScope
import com.andreyyurko.firstapp.data.network.response.error.CreateProfileErrorResponse
import com.andreyyurko.firstapp.entity.ProfileInformation
import com.andreyyurko.firstapp.interactor.AuthInteractor
import com.andreyyurko.firstapp.ui.base.BaseViewModel
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authInteractor: AuthInteractor
) : BaseViewModel() {

    private val _signUpActionStateFlow = MutableStateFlow<SignUpActionState>(SignUpActionState.Loading)
    private var _email : String? = null

    fun signUpActionStateFlow() : Flow<SignUpActionState> {
        return _signUpActionStateFlow.asStateFlow()
    }

    suspend fun refreshSuccess() {
        _signUpActionStateFlow.emit(SignUpActionState.NotVerifiedYet)
    }

    fun signUp(
        email: String,
        verificationToken: String,
        firstName: String,
        lastName: String,
        nickName: String,
        password: String
    ) {
        viewModelScope.launch {
            _signUpActionStateFlow.emit(SignUpActionState.Loading)
            try {
                when (val response = authInteractor.createProfile(
                    email = email,
                    verificationToken = verificationToken,
                    firstName = firstName,
                    lastName = lastName,
                    nickName = nickName,
                    password = password
                )) {
                    is NetworkResponse.Success -> {
                        _signUpActionStateFlow.emit(SignUpActionState.SignUpSuccess)
                    }
                    is NetworkResponse.ServerError -> {
                        _signUpActionStateFlow.emit(SignUpActionState.ServerError(response))
                    }
                    is NetworkResponse.NetworkError -> {
                        _signUpActionStateFlow.emit(SignUpActionState.NetworkError(response))
                    }
                    is NetworkResponse.UnknownError -> {
                        _signUpActionStateFlow.emit(SignUpActionState.UnknownError(response))
                    }
                }
            } catch (error: Throwable) {
                Timber.e(error)
                _signUpActionStateFlow.emit(SignUpActionState.UnknownError(NetworkResponse.UnknownError(error)))
            }
        }
    }

    sealed class SignUpActionState {
        object SignUpSuccess : SignUpActionState()
        object Loading : SignUpActionState()
        object NotVerifiedYet : SignUpActionState()
        data class ServerError(val e: NetworkResponse.ServerError<CreateProfileErrorResponse>) : SignUpActionState()
        data class NetworkError(val e: NetworkResponse.NetworkError) : SignUpActionState()
        data class UnknownError(val e: NetworkResponse.UnknownError) : SignUpActionState()
    }
}