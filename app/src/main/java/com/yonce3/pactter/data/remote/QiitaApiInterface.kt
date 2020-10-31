package com.yonce3.pactter.data.remote

import retrofit2.http.GET

interface QiitaApiInterface {

    @GET()
    fun getArticles():

}