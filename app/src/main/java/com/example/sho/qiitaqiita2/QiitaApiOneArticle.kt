package com.example.sho.qiitaqiita2

import retrofit2.http.GET


interface QiitaApiOneArticle {

    @GET("api/v2/items?page=1&per_page=1")
    suspend fun items(): List<Article>
}