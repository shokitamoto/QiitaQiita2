package com.example.sho.qiitaqiita2

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sho.qiitaqiita2.databinding.ListEmptyFavoriteBinding
import com.example.sho.qiitaqiita2.databinding.ListItemFavoriteBinding

class FavoritesView: RecyclerView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * customAdapter
     */



    val customAdapter by lazy { Adapter(context) } // { Adapter(context) }は、初期化の方法。
    // customAdapter のクラスはAdapter。「val customAdapter = Adapter(context)」
//    customAdapterはAdapterのインスタンス
    // by lazyなしで書くと、val customAdapter = Adapter(context)
    // by lazyで書くのは、Repository系が多い。ユーザーの操作によって、表示が変わる場合に使う。ユーザーが押さなかったら、メモリは節約できる。
    // by lazyは処理を遅らせて、他の処理が行われている時の負担を減らすことができる。

    // layoutManagerの実装
    // initはFavoritesViewがインスタンス化されて、コンストラクターが呼ばれた後に呼ばれる。この場合は、initよりも先に書かれているval customAdapter by lazy { Adapter(context) } が先に呼ばれる。
    // initはどんなクラスにもある。
    init {
        adapter = customAdapter
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
    }
    // layoutManagerの実装ここまで

    // RecyclerView.ViewHolderの"ViewHolder"と、onCreateViewHolderの返り値・onBindViewHolderの第一引数は必ず一致する。
    class Adapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        private val items = mutableListOf<Favorite>() // RecyclerViewで表示するリスト

        //clickをFragmentに伝えるためのもの
        var onClickFavorite: ((Favorite) -> Unit)? = null

        // refreshはAdapterに対して、新しいリストを渡している。
        fun refresh(list: List<Favorite>) {
            items.apply {
                clear()
                addAll(list)
            }
            notifyDataSetChanged() // 全て再描画する。こいつを呼ぶことで、getItemCount()がデータの変更を認識する
        }

        override fun getItemCount(): Int = if (items.isEmpty()) 1 else items.size

        override fun getItemViewType(position: Int): Int {
            //裏で、getItemViewTypeとonCreateViewHolderがviewTypeで繋がっている。
            //getItemViewTypeの初期値は0。
            return if (items.isEmpty())
                VIEW_TYPE_EMPTY
            else
                VIEW_TYPE_ITEM
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // viewType 1 or 2。getItemViewTypeを書かないと0がくる。

            val view = View(context)
            if (viewType == VIEW_TYPE_EMPTY) {
                // viewTypeが1のとき
                return EmptyViewHolder(ListEmptyFavoriteBinding.inflate(LayoutInflater.from(context), parent, false))
            }

            // FavoritesViewクラスはRecyclerViewの継承したクラス。
            // そのクラスはcontextを持っていて(当ページの先頭の方のやつ)、何も宣言しなくていいのが利点。
            // viewTypeが2のとき
            return ItemViewHolder(ListItemFavoriteBinding.inflate(LayoutInflater.from(context),parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (holder is ItemViewHolder) {
                updateItemViewHolder(holder, position)
            } else if (holder is EmptyViewHolder) {
                updateEmptyViewHolder(holder)
            }
        }
        private fun updateEmptyViewHolder(holder: EmptyViewHolder){
            // 固定値なので、なくてもいい
        }
        private fun updateItemViewHolder(holder: ItemViewHolder, position: Int) {
            val data = items[position]
            holder.binding.apply {
                userName.text = data.id
                articleContent.text = data.articleContent
                articleTitle.text = data.articleTitle
                isFavorite = Favorite.findBy(data.id) != null //お気に入りの状態を代入する nullだったらお気に入りじゃない。
                favoriteImageView.setOnClickListener {
                    Favorite.insert(data)
                    notifyItemChanged(position) // position番目だけ再描画
                }
                unFavoriteImageView.setOnClickListener {
                    Favorite.delete(data.id)
                    notifyItemChanged(position) // position番目だけ再描画
                }
                root.setOnClickListener {
                    // 同じクラス内でonClickFavoriteを使うので、新しく宣言する必要はない
                    // ArticleListAdapterの場合は、ViewHolderで再宣言している。
                    onClickFavorite?.invoke(data)
                }
            }
        }
        class ItemViewHolder(val binding: ListItemFavoriteBinding): RecyclerView.ViewHolder(binding.root)
        class EmptyViewHolder(val binding: ListEmptyFavoriteBinding): RecyclerView.ViewHolder(binding.root)


        companion object {
            private const val VIEW_TYPE_EMPTY = 1
            private const val VIEW_TYPE_ITEM = 2

        }
    }
}