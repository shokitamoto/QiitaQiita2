package com.example.sho.qiitaqiita2

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

class ArticleListAdapter(
    private val layoutInflater,
    private val articleList: ArrayList<Article>
) : RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {

    // var binding: ListItemArticleBinding? = null

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
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(articleList[position])
    }

    class ViewHolder(
        private val binding: ListItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.userName.text = article.account.username
            binding.content.text = article.content
        }
    }
}
