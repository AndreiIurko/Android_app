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
    private val _emailConfirmationActionStateFlow = MutableStateFlow<SubmissionCodeActionState>(SubmissionCodeActionState.Loading)

    private val _codeSendingActionStateFlow = MutableStateFlow<SendingCodeActionState>(SendingCodeActionState.Loading)

    fun confirmationActionStateFlow(): Flow<SubmissionCodeActionState> {
        return _emailConfirmationActionStateFlow.asStateFlow()
    }

    fun sendingActionStateFlow(): Flow<SendingCodeActionState> {
        return _codeSendingActionStateFlow.asStateFlow()
    }

    fun submitCode(
        email: String,
        firstName: String,
        lastName: String,
        userName: String,
        password: String,
        code: String
    ) {
        viewModelScope.launch {
            _emailConfirmationActionStateFlow.emit(SubmissionCodeActionState.Loading)
            try {
                when (val response = authInteractor.verifyRegistrationCode(email, code)) {
                    is NetworkResponse.Success -> {
                        val verificationToken = response.body.verificationToken
                        createProfile(
                            email = email,
                            verificationToken = verificationToken,
                            firstName = firstName,
                            lastName = lastName,
                            userName = userName,
                            password = password
                        )
                    }
                    is NetworkResponse.ServerError -> {
                        _emailConfirmationActionStateFlow.emit(SubmissionCodeActionState.VerificationServerError(response))
                    }
                    is NetworkResponse.NetworkError -> {
                        _emailConfirmationActionStateFlow.emit(SubmissionCodeActionState.NetworkError(response))
                    }
                    is NetworkResponse.UnknownError -> {
                        _emailConfirmationActionStateFlow.emit(SubmissionCodeActionState.UnknownError(response))
                    }
                }
            } catch (error: Throwable) {
                Timber.e(error)
                _emailConfirmationActionStateFlow.emit(SubmissionCodeActionState.UnknownError(NetworkResponse.UnknownError(error)))
            }
        }
    }

    private suspend fun createProfile(
        email: String,
        verificationToken: String,
        firstName: String,
        lastName: String,
        userName: String,
        password: String
    ) {
        try {
            when (val response = authInteractor.createProfile(
                email = email,
                verificationToken = verificationToken,
                firstName = firstName,
                lastName = lastName,
                userName = userName,
                password = password
            )) {
                is NetworkResponse.Success -> {
                    _emailConfirmationActionStateFlow.emit(SubmissionCodeActionState.SubmissionCodeSuccess)
                }
                is NetworkResponse.ServerError -> {
                    _emailConfirmationActionStateFlow.emit(SubmissionCodeActionState.CreateProfileServerError(response))
                }
                is NetworkResponse.NetworkError -> {
                    _emailConfirmationActionStateFlow.emit(SubmissionCodeActionState.NetworkError(response))
                }
                is NetworkResponse.UnknownError -> {
                    _emailConfirmationActionStateFlow.emit(SubmissionCodeActionState.UnknownError(response))
                }
            }
        } catch (error: Throwable) {
            Timber.e(error)
            _emailConfirmationActionStateFlow.emit(SubmissionCodeActionState.UnknownError(NetworkResponse.UnknownError(error)))
        }
    }

    fun sendCodeAgain(email: String) {
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

    sealed class SubmissionCodeActionState {
        object SubmissionCodeSuccess : SubmissionCodeActionState()
        object Loading : SubmissionCodeActionState()
        data class VerificationServerError(val e: NetworkResponse.ServerError<VerifyRegistrationCodeErrorResponse>) : SubmissionCodeActionState()
        data class CreateProfileServerError(val e: NetworkResponse.ServerError<CreateProfileErrorResponse>) : SubmissionCodeActionState()
        data class NetworkError(val e: NetworkResponse.NetworkError) : SubmissionCodeActionState()
        data class UnknownError(val e: NetworkResponse.UnknownError) : SubmissionCodeActionState()
    }

    sealed class SendingCodeActionState {
        object SendingSuccess : SendingCodeActionState()
        object Loading : SendingCodeActionState()
        data class SendingServerError(val e: NetworkResponse.ServerError<SendRegistrationVerificationCodeErrorResponse>) : SendingCodeActionState()
        data class NetworkError(val e: NetworkResponse.NetworkError) : SendingCodeActionState()
        data class UnknownError(val e: NetworkResponse.UnknownError) : SendingCodeActionState()
    }
}