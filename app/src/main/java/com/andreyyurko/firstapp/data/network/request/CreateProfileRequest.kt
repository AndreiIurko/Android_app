package com.andreyyurko.firstapp.data.network.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateProfileRequest(
    @Json(name = "verification_token") val verificationToken: String,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    @Json(name = "user_name") val userName: String,
    @Json(name = "email") val email: String?,
    @Json(name = "password") val password: String
)