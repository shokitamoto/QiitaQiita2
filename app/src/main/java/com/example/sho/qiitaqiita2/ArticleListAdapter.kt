package com.example.sho.qiitaqiita2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sho.qiitaqiita2.databinding.ListItemArticleBinding

//アダプター...RecyclerViewでリスト表示する時の表示のさせ方を決める。作って学ぶp.88

class ArticleListAdapter(
    private val layoutInflater: LayoutInflater,
    private val articleList: ArrayList<Article>
) : RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {

    // var binding: ListItemArticleBinding? = null

    // Articleが引数で、Unitが戻り値
    // fun test(article: Article, index:Int): String{}的な
    var onClickArticle: ((Article) -> Unit)? = null  //変数に関数を代入した形

    override fun getItemCount() = articleList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        var binding = DataBindingUtil.inflate<ListItemArticleBinding>(
            layoutInflater,
            R.layout.list_item_article,
            parent,
            false
        )
        // 戻り値は、ViewHolderの　インスタンス
        return ViewHolder(binding, onClickArticle)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(articleList[position])
    }

    class ViewHolder(
        private val binding: ListItemArticleBinding,
        private val onClickArticle: ((Article) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.apply {
                userName.text = article.id
                articleContent.text = article.articleContent
                articleTitle.text = article.articleTitle
                root.setOnClickListener {
                    onClickArticle?.invoke(article)
                    // invokeは、onClickArticleを起動する、の意味
                }
            }


        }
    }
}
