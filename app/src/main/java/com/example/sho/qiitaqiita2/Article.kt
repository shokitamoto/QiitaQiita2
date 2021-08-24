package com.example.sho.qiitaqiita2

import com.squareup.moshi.Json

class     Article {

    @Json(name = "id") //一つ下のidと同じ名前なので省略可能
    var id = "" // 記事ID
    var userName = "" // ユーザーネーム


    @Json(name = "title") var articleTitle = "" // タイトル
    @Json(name = "body")
    var articleContent ="" // 記事文章

    var url = ""

    //このままだとcom.example.sho.qiitaqiita2.Article@ajfeo234
    //のようにハッシュコードで表示されるが、
    //以下のtoString()の処理をすることで、idとタイトルが見えるようになる。
    override fun toString(): String {
        return "id:$id, title:${articleTitle}"
    }
}