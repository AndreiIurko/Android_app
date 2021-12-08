package com.andreyyurko.firstapp.ui.emailconfirmation

import androidx.lifecycle.viewModelScope
import com.andreyyurko.firstapp.data.network.response.error.CreateProfileErrorResponse
import com.andreyyurko.firstapp.data.network.response.error.SendRegistrationVerificationCodeErrorResponse
import com.andreyyurko.firstapp.data.network.response.error.VerifyRegistrationCodeErrorResponse
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
class EmailConfirmationViewModel @Inject constructor(
    private val authInteractor: AuthInteractor
) : BaseViewModel() {
    private val _emailConfirmationActionStateFlow = MutableStateFlow<ConfirmationCodeActionState>(ConfirmationCodeActionState.Loading)

    private val _codeSendingActionStateFlow = MutableStateFlow<SendingCodeActionState>(SendingCodeActionState.Loading)

    fun confirmationActionStateFlow(): Flow<ConfirmationCodeActionState> {
        return _emailConfirmationActionStateFlow.asStateFlow()
    }

    fun sendingActionStateFlow(): Flow<SendingCodeActionState> {
        return _codeSendingActionStateFlow.asStateFlow()
    }

    fun submitCode(
        email: String,
        code: String
    ) {
        viewModelScope.launch {
            _emailConfirmationActionStateFlow.emit(ConfirmationCodeActionState.Loading)
            try {
                when (val response = authInteractor.verifyRegistrationCode(email, code)) {
                    is NetworkResponse.Success -> {
                        _emailConfirmationActionStateFlow.emit(ConfirmationCodeActionState.SubmissionCodeSuccess)
                    }
                    is NetworkResponse.ServerError -> {
                        _emailConfirmationActionStateFlow.emit(ConfirmationCodeActionState.VerificationServerError(response))
                    }
                    is NetworkResponse.NetworkError -> {
                        _emailConfirmationActionStateFlow.emit(ConfirmationCodeActionState.NetworkError(response))
                    }
                    is NetworkResponse.UnknownError -> {
                        _emailConfirmationActionStateFlow.emit(ConfirmationCodeActionState.UnknownError(response))
                    }
                }
            } catch (error: Throwable) {
                Timber.e(error)
                _emailConfirmationActionStateFlow.emit(ConfirmationCodeActionState.UnknownError(NetworkResponse.UnknownError(error)))
            }
        }
    }



    fun sendCode(email: String) {
        viewModelScope.launch {
            _codeSendingActionStateFlow.emit(SendingCodeActionState.Loading)
            try {
                when (val response = authInteractor.sendRegistrationVerificationCode(email)) {
                    is NetworkResponse.Success -> {
                        _codeSendingActionStateFlow.emit(SendingCodeActionState.SendingSuccess)
                    }
                    is NetworkResponse.ServerError -> {
                        _codeSendingActionStateFlow.emit(SendingCodeActionState.SendingServerError(response))
                    }
                    is NetworkResponse.NetworkError -> {
                        _codeSendingActionStateFlow.emit(SendingCodeActionState.NetworkError(response))
                    }
                    is NetworkResponse.UnknownError -> {
                        _codeSendingActionStateFlow.emit(SendingCodeActionState.UnknownError(response))
                    }
                }
            } catch (error: Throwable) {
                Timber.e(error)
                _codeSendingActionStateFlow.emit(SendingCodeActionState.UnknownError(NetworkResponse.UnknownError(error)))
            }
        }
    }

    suspend fun refreshSuccess() {
        _emailConfirmationActionStateFlow.emit(ConfirmationCodeActionState.Loading)
    }

    sealed class ConfirmationCodeActionState {
        object SubmissionCodeSuccess : ConfirmationCodeActionState()
        object Loading : ConfirmationCodeActionState()
        data class VerificationServerError(val e: NetworkResponse.ServerError<VerifyRegistrationCodeErrorResponse>) : ConfirmationCodeActionState()
        data class NetworkError(val e: NetworkResponse.NetworkError) : ConfirmationCodeActionState()
        data class UnknownError(val e: NetworkResponse.UnknownError) : ConfirmationCodeActionState()
    }

    sealed class SendingCodeActionState {
        object SendingSuccess : SendingCodeActionState()
        object Loading : SendingCodeActionState()
        data class SendingServerError(val e: NetworkResponse.ServerError<SendRegistrationVerificationCodeErrorResponse>) : SendingCodeActionState()
        data class NetworkError(val e: NetworkResponse.NetworkError) : SendingCodeActionState()
        data class UnknownError(val e: NetworkResponse.UnknownError) : SendingCodeActionState()
    }

}