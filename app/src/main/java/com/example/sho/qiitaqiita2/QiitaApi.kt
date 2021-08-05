package com.example.sho.qiitaqiita2

import retrofit2.http.GET
import retrofit2.http.Query

interface QiitaApi {

    @GET("api/v2/items")
    suspend fun items(
    ): List<Article>

}