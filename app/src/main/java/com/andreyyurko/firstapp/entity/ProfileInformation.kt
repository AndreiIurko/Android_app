package com.andreyyurko.firstapp.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileInformation(
    @Json(name = "firstname") val firstName : String,
    @Json(name = "lastname") val lastName : String,
    @Json(name = "nickName") val nickName : String,
    @Json(name = "email") val email : String,
    @Json(name = "password") val password : String
)