package com.andreyyurko.firstapp

import com.andreyyurko.firstapp.entity.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.GET

interface Api {

    @GET("users?per_page=10")
    suspend fun getUsers(): GetUsersResponse
}

@JsonClass(generateAdapter = true)
data class GetUsersResponse(
    @Json(name = "data") val data: List<User>
)