package com.yonce3.pactter.data.repository

import androidx.lifecycle.LiveData
import com.yonce3.pactter.data.entity.Article
import com.yonce3.pactter.data.remote.QiitaApiInterface
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class QiitaRepository() {
    private var service: QiitaApiInterface = Retrofit.Builder()
        .baseUrl("https://qiita.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(QiitaApiInterface::class.java)

    suspend fun getArticles(query: String): Article? {
        return service.getArticles(query)
    }
}