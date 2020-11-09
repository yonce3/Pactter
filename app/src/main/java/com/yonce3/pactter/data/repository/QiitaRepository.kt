package com.yonce3.pactter.data.repository

import android.util.Log
import com.yonce3.pactter.data.entity.Article
import com.yonce3.pactter.data.entity.Articles
import com.yonce3.pactter.data.remote.QiitaApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

class QiitaRepository() {
    private var service: QiitaApiInterface = Retrofit.Builder()
        .baseUrl("https://qiita.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(QiitaApiInterface::class.java)

    suspend fun getArticles(query: String?, callback: (List<Article>) -> Unit) {
        service.getArticles(query).enqueue(object : Callback<List<Article>> {
            override fun onResponse(call: Call<List<Article>>, response: Response<List<Article>>) {
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            callback(it)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Article>>, t: Throwable) {
                Log.e("API", "GET ERROR")
            }
        })
    }

    suspend fun getArticles(query: String?): List<Article>? {
        try {
            val response = service.getArticles(query).execute()
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