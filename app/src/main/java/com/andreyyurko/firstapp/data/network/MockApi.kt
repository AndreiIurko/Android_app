package com.andreyyurko.firstapp.data.network

import com.andreyyurko.firstapp.data.network.request.CreateProfileRequest
import com.andreyyurko.firstapp.data.network.request.RefreshAuthTokensRequest
import com.andreyyurko.firstapp.data.network.request.SignInWithEmailRequest
import com.andreyyurko.firstapp.data.network.response.error.*
import com.andreyyurko.firstapp.entity.AuthTokens
import com.haroldadmin.cnradapter.NetworkResponse
import com.andreyyurko.firstapp.data.network.response.VerificationTokenResponse

class MockApi : Api {
    override suspend fun getUsers(): GetUsersResponse {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithEmail(request: SignInWithEmailRequest): NetworkResponse<AuthTokens, SignInWithEmailErrorResponse> {
        return NetworkResponse.Success(
            AuthTokens(
                accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dnZWRJbkFzIjoiYWRtaW4iLCJpYXQiOjE0MjI3Nzk2MzgsImV4cCI6MTY0MDg3MTc3MX0.gzSraSYS8EXBxLN_oWnFSRgCzcmJmMjLiuyu5CSpyHI",
                refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dnZWRJbkFzIjoiYWRtaW4iLCJpYXQiOjE0MjI3Nzk2MzgsImV4cCI6MTY0MDg3MTc3MX0.gzSraSYS8EXBxLN_oWnFSRgCzcmJmMjLiuyu5CSpyHI",
                accessTokenExpiration = 1640871771000,
                refreshTokenExpiration = 1640871771000,
            ),
            code = 200
        )
    }

    override suspend fun refreshAuthTokens(request: RefreshAuthTokensRequest): NetworkResponse<AuthTokens, RefreshAuthTokensErrorResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun sendRegistrationVerificationCode(email: String): NetworkResponse<Unit, SendRegistrationVerificationCodeErrorResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun verifyRegistrationCode(
        code: String,
        email: String?
    ): NetworkResponse<VerificationTokenResponse, VerifyRegistrationCodeErrorResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun createProfile(request: CreateProfileRequest): NetworkResponse<AuthTokens, CreateProfileErrorResponse> {
        TODO("Not yet implemented")
    }
}