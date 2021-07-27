package com.example.sho.qiitaqiita2.ActivityandFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.sho.qiitaqiita2.QiitaApi
import com.example.sho.qiitaqiita2.R
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AllFragment : Fragment() {

    // Repositoryなしで書く実験------------------------------------------------------------------
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
        retrofit.create(QiitaApi::class.java)



    // ここまで---------------------------------------------------------------------------------------------------



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding : FragmentAllBinding? = null

        fun loadData(page: Int = 1) {
            binding = DataBindingUtil.bind(view)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val list = api.items(page, "Android")
                    withContext(Dispatchers.Main) {
                        binding?.textViewAll?.text = list
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        loadData(1)
    }


}