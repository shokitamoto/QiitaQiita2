package com.example.sho.qiitaqiita2.activityandfragment

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sho.qiitaqiita2.*
import com.example.sho.qiitaqiita2.databinding.ActivityOneArticleBinding
import com.example.sho.qiitaqiita2.databinding.FragmentArticleListBinding
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class OneArticleActivity: AppCompatActivity(R.layout.activity_one_article) {

    private val httpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    // Moshi.BuilderでMoshiのインスタンスを形成
    private val moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    // APIにアクセス(Retrofit使用)
    private val retrofit =
        Retrofit.Builder()
            .baseUrl("https://qiita.com")
            // Moshiを使って、JSONをパースするようにRetrofitに登録(追加）
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient)
            .build()

    // APIにアクセス(retrofitを使用)
    private val api =
        retrofit.create(QiitaApiOneArticle::class.java)

    // メンバ変数をここで用意---------------------------------------------------------------------------------------
    private var binding: ActivityOneArticleBinding? = null

    private val coroutineScope = CoroutineScope(Dispatchers.IO) //coroutineを変数に

    private lateinit var layoutManager: LinearLayoutManager // 並べ方決めるやつ

    private val articleList = ArrayList<Article>()
    // ここまで---------------------------------------------------------------------------------------------------


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val bindingData: ActivityOneArticleBinding? = DataBindingUtil.bind(savedInstanceState)
        binding = bindingData ?: return // bindingがnullなら何も返さない

        coroutineScope.launch {
            val articleListResponse = api.items()
            articleList.addAll(articleListResponse)
        }

    }

    override fun onDestroy(){
        super.onDestroy()
        binding?.unbind()
    }


}