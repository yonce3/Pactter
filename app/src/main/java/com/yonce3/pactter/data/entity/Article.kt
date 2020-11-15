package com.yonce3.pactter.data.entity

import com.squareup.moshi.Json

data class Article (
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "url") val url: String,
    @Json(name = "user") val user: QiitaUser,
)

data class QiitaUser (
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "profile_image_url") val profile_image_url: String,
)