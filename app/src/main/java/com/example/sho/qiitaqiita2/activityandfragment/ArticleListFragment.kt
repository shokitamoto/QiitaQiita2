package com.example.sho.qiitaqiita2.activityandfragment

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sho.qiitaqiita2.Article
import com.example.sho.qiitaqiita2.ArticleListAdapter
import com.example.sho.qiitaqiita2.QiitaApi
import com.example.sho.qiitaqiita2.R
import com.example.sho.qiitaqiita2.databinding.FragmentAllBinding
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

class ArticleListFragment: Fragment(R.layout.fragment_article_list) {

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

    // メンバ変数をここで用意---------------------------------------------------------------------------------------
    private var binding: FragmentArticleListBinding? = null

    private val coroutineScope = CoroutineScope(Dispatchers.IO) //coroutineを変数に

    private lateinit var adapter: ArticleListAdapter // Adapterをlateinitで変数に。

    private lateinit var layoutManager: LinearLayoutManager // 並べ方決めるやつ

    private val articleList = ArrayList<Article>()
    // ここまで---------------------------------------------------------------------------------------------------


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ArticleListAdapter(layoutInflater, articleList)
        adapter.onClickArticle = { article ->
            showArticleDetail(article.url)
        }


        layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        val bindingData: FragmentArticleListBinding? = DataBindingUtil.bind(view)
        binding = bindingData ?: return // bindingがnullなら何も返さない

        // ArticleListAdapter(表示内容)とLayoutManager(レイアウト方法)をRecyclerViewに設定する。
        bindingData.recyclerView.also {
            it.layoutManager = layoutManager
            it.adapter = adapter
        }

        coroutineScope.launch {
            val articleListResponse = api.items()
            articleList.addAll(articleListResponse)
            reloadArticleList()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding?.unbind()
    }

    private suspend fun reloadArticleList() = withContext(Dispatchers.Main){
        adapter.notifyDataSetChanged()  //通知。データセットが変わったよ→リストの表示が更新される。
    }

    private fun showArticleDetail(url: String) {
        OneArticleActivity.start(requireActivity(), url)
    }


}