package com.yonce3.pactter.data.remote

import com.yonce3.pactter.data.entity.Article
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QiitaApiInterface {

    @GET("/api/v2/items")
    suspend fun getArticles(
        @Query(value = "") query: String,
    ): Article

}