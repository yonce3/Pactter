package com.yonce3.pactter.data.remote

import androidx.lifecycle.LiveData
import com.yonce3.pactter.data.entity.Article
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QiitaApiInterface {

    @GET("/api/v2/items")
    suspend fun getArticles(
        @Query("query") query: String?
    ): Response<List<Article>>

}