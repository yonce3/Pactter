package com.yonce3.pactter.data.entity

import com.squareup.moshi.Json

data class Article (
    val id: String,
    val title: String,
    @Json(name = "user") val user: QiitaUser,
)

data class QiitaUser (
    val id: String,
    val name: String,
    val profile_image_url: String,
)