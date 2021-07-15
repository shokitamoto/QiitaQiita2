package com.example.sho.qiitaqiita2

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ItemRepository {

    private var itemService: ItemService

    init {
        val okHttpClient = OkHttpClient.Builder().build()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://qiita.com/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
        itemService = retrofit.create(ItemService::class.java)
    }

    //エラー処理は省いている
    fun getItemList(callback: (List<ItemEntity>) -> Unit) {
        itemService.items(page = 1, perPage = 10).enqueue(object : Callback<List<ItemEntity>> {

            override fun onResponse(call: Call<List<ItemEntity>>?, response: Response<List<ItemEntity>>?) {
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            callback(it)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemEntity>>?, t: Throwable?) {}
        })

    }
}