package com.yonce3.pactter.data.repository

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yonce3.pactter.data.entity.Article
import com.yonce3.pactter.data.remote.QiitaApiInterface
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

class QiitaRepository() {

    lateinit var service: QiitaApiInterface
    init {
        var moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        service = Retrofit.Builder()
            .baseUrl("https://qiita.com/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(QiitaApiInterface::class.java)
    }

    fun getArticles(query: String?): List<Article>? {
        try {
            val response = service.getArticles(query).execute()
            if (response.isSuccessful) {
                return response.body()
            } else {
                Log.d("QiitaRepository", "Get Error")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}