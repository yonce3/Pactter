package com.yonce3.pactter.data.entity

data class Article (
    val id: String,
    val title: String,
    val user: QiitaUser,
)

data class QiitaUser (
    val id: String,
    val name: String,
    val profile_image_url: String,
)