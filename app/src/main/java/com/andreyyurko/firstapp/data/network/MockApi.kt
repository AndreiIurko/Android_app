package com.andreyyurko.firstapp.data.network

import com.andreyyurko.firstapp.data.network.request.CreateProfileRequest
import com.andreyyurko.firstapp.data.network.request.RefreshAuthTokensRequest
import com.andreyyurko.firstapp.data.network.request.SignInWithEmailRequest
import com.andreyyurko.firstapp.data.network.response.error.*
import com.andreyyurko.firstapp.entity.AuthTokens
import com.haroldadmin.cnradapter.NetworkResponse
import com.andreyyurko.firstapp.data.network.response.VerificationTokenResponse
import com.andreyyurko.firstapp.entity.User

class MockApi : Api {
    override suspend fun getUsers(): NetworkResponse<List<User>, GetUsersErrorResponce> {
        return NetworkResponse.Success(
            body = listOf(
                User(
                    userName = "Teacher",
                    avatarUrl = "https://mir-s3-cdn-cf.behance.net/project_modules/fs/12f7be28757683.55d10db422494.jpg",
                    groupName = "Best of the best"
                ),
                User(
                    userName = "Student1",
                    avatarUrl = "https://avatars.mds.yandex.net/i?id=a87c16c1a58d48bac66733e3cbb564a9-4425107-images-thumbs&n=13",
                    groupName = "?"
                )
            ),
            code = 200
        )
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
        return NetworkResponse.Success(
            body = Unit,
            code = 200
        )
    }

    override suspend fun verifyRegistrationCode(
        code: String,
        email: String?
    ): NetworkResponse<VerificationTokenResponse, VerifyRegistrationCodeErrorResponse> {
        return NetworkResponse.Success(
            body = VerificationTokenResponse("OK"),
            code = 200
        )
    }

    override suspend fun createProfile(request: CreateProfileRequest): NetworkResponse<AuthTokens, CreateProfileErrorResponse> {
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
}