//package com.example.sho.qiitaqiita2
//
//import android.util.Log
//import android.widget.TextView
//import androidx.databinding.DataBindingUtil
//import com.squareup.moshi.KotlinJsonAdapterFactory
//import com.squareup.moshi.Moshi
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Dispatchers.IO
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory
//
//class AllArticleRepository {
//
//    private val httpClient =
//        OkHttpClient.Builder()
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            })
//            .build()
//
//    // Moshi.BuilderでMoshiのインスタンスを形成
//    private val moshi =
//        Moshi.Builder()
//            .add(KotlinJsonAdapterFactory())
//            .build()
//
//    // APIにアクセス(Retrofit使用)
//    private val retrofit =
//        Retrofit.Builder()
//            .baseUrl("https://qiita.com")
//            // Moshiを使って、JSONをパースするようにRetrofitに登録(追加）
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .client(httpClient)
//            .build()
//
//    // APIにアクセス(retrofitを使用)
//    private val api =
//        retrofit.create(QiitaApi::class.java)
//
//    fun loadData(page: Int = 1) {
//        val binding = DataBindingUtil.bind(page)
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val list = api.items(page, "Android")
//                withContext(Dispatchers.Main) {
//                    binding?.textViewAll?.text = list
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//}
