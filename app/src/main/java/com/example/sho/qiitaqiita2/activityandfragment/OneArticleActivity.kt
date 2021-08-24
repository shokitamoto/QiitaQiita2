package com.example.sho.qiitaqiita2.activityandfragment

import android.app.Activity
import android.content.Intent
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

class OneArticleActivity: AppCompatActivity() {

//    通信は再度必要なし
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
//        retrofit.create(QiitaApiOneArticle::class.java)

    // メンバ変数をここで用意---------------------------------------------------------------------------------------
    private lateinit var binding: ActivityOneArticleBinding // Fragmentと違ってActivityではclass名の後ろには設定できないのでメンバ変数にする。


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        以下はActivityの時の書き方
        binding = DataBindingUtil.setContentView(this, R.layout.activity_one_article)

        // intentからUrlを取得
        val url = intent.getStringExtra(KEY_URL) ?: run {
//            urlがnulだった場合、このactivityが終わるようになっている。
            finish()
            return
        }

        // TODO: urlを使ってwebViewのloadをする
        binding.webView.apply {
            // TODO:webViewの設定をする


            loadUrl(url)

//        以下はFragmentの時の書き方
//        val bindingData: ActivityOneArticleBinding? = DataBindingUtil.bind(savedInstanceState)
//        binding = bindingData ?: return // bindingがnullなら何も返さない

//        データ取得もしないので、コルーチンも必要なし
//        coroutineScope.launch {
//            val articleListResponse = api.items()
//            articleList.addAll(articleListResponse)
        }
    }

    //ここにstartを用意することで(メソッドの呼び出し位置の一元化)、どこからstartメソッドが呼ばれているかが超わかりやすくなる
    companion object{  //クラスが持っている変数とかメソッド　javaだとstaticという
        // key_urlのタイプミスを防ぐためにKEY_URLという定数を用意し、それをonCreate内のurlに代入する。
        // そうすることで、コードが間違っていて気づけない問題を防ぐことができる。
        private const val KEY_URL = "key_url"

//        startメソッドを使うと、urlをactivityに渡す感じで、このActivityが開かれる

        fun start(activity: Activity, url: String) {
            val intent = Intent(activity, OneArticleActivity::class.java)
            intent.putExtra(KEY_URL, url) // intentにUrlをつめる
            activity.startActivity(intent)

        }
    }
//    Activityの場合、unbindしなくてもいい
//    override fun onDestroy(){
//        super.onDestroy()
//        binding?.unbind()
//    }


}