package com.andreyyurko.firstapp.interactor

import com.haroldadmin.cnradapter.NetworkResponse
import com.andreyyurko.firstapp.data.network.response.error.SignInWithEmailErrorResponse
import com.andreyyurko.firstapp.entity.AuthTokens
import com.andreyyurko.firstapp.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend fun isAuthorized(): Flow<Boolean> =
        authRepository.isAuthorizedFlow()

    suspend fun signInWithEmail(email: String, password: String): NetworkResponse<AuthTokens, SignInWithEmailErrorResponse> {
        val response = authRepository.generateAuthTokensByEmail(email, password)
        when (response) {
            is NetworkResponse.Success -> {
                authRepository.saveAuthTokens(response.body)
            }
            is NetworkResponse.Error -> {
                Timber.e(response.error)
            }
        }
        return response
    }

    suspend fun logout() {
        authRepository.saveAuthTokens(null)
    }
}