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

    private var service: QiitaApiInterface = Retrofit.Builder()
        .baseUrl("https://qiita.com")
        .addConverterFactory(MoshiConverterFactory.create())
//            Moshi.Builder().add(
//                KotlinJsonAdapterFactory()
//            ).build()
//        ))
        .build()
        .create(QiitaApiInterface::class.java)

    suspend fun getArticles(query: String?): List<Article>? {
        val response = service.getArticles(query).execute()
        try {
            if (response.isSuccessful) {
                return response.body()
            } else {
                Log.d("QiitaRepository", "GET ERROR")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}