package com.example.sho.qiitaqiita2

import android.telecom.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ItemService {

    //特に RxJava 等は使わず、Retrofit 標準のコールバックで結果を受け取る
    @GET("items")
    fun items(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<List<ItemEntity>>


}