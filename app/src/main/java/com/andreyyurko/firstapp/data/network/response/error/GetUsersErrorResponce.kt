package com.andreyyurko.firstapp.data.network.response.error

import com.andreyyurko.firstapp.entity.Error
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetUsersErrorResponce (
    @Json(name = "non_field_errors") val nonFieldErrors: List<Error>?
)