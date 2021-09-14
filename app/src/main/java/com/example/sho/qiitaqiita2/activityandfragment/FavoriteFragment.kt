package com.example.sho.qiitaqiita2.activityandfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sho.qiitaqiita2.Favorite
import com.example.sho.qiitaqiita2.R
import com.example.sho.qiitaqiita2.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    // 今までやってたのとは違うFragmentのbinding方法
    private lateinit var binding: FragmentFavoriteBinding // lateinitでで初期化のタイミングをonViewCreatedまで遅らせる。そうすることでメンバ変数の時点では初期化をする必要がなくなる(null許容にしなくて済む）
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentFavoriteBinding.inflate(inflater, container, false) //初期化はこのタイミング。操作を行うのは必ず初期化の後。じゃないとクラッシュする。
        return binding.root // 戻り値がView?なので、binding.rootはViewのはず。根っこ。fragment_favorite.xmlのconstraintlayoutのこと。
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                // 上にswipeした時にデータ更新
                updateData()
            }
            favoritesView.customAdapter.onClickFavorite = {
                showFavoriteDetail(it)
            }
        }
        // ページが新しくなった時にデータ更新
//        updateData()
    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    private fun updateData() {
        val list = Favorite.findAll()
        binding.favoritesView.customAdapter.refresh(list)

    }

    private fun showFavoriteDetail(favorite: Favorite) {
        OneArticleActivity.start(requireActivity(), favorite.url, favorite.articleTitle, favorite.id, favorite.articleContent)

    }

}