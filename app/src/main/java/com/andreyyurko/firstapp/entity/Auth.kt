package com.andreyyurko.firstapp.entity

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    @Transient val accessTokenExpiration: Long,
    @Transient val refreshTokenExpiration: Long
) {

    fun isAccessTokenExpired(): Boolean = (accessTokenExpiration <= System.currentTimeMillis())
    fun isRefreshTokenExpired(): Boolean = (refreshTokenExpiration <= System.currentTimeMillis())

}