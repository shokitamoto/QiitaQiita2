package com.example.sho.qiitaqiita2

import retrofit2.http.GET
import retrofit2.http.Query

interface QiitaApi {

    @GET("api/v2/items")
    suspend fun items(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("per_page") perPage: Int = 20
    ): List<Article>

}