package com.example.sho.qiitaqiita2

class     Article {
    var id = "" // 記事ID
    var userName = "" // ユーザーネーム
    var articleTitle = "" // タイトル
    var articleContent ="" // 記事文章

    //このままだとcom.example.sho.qiitaqiita2.Article@ajfeo234
    //のようにハッシュコードで表示されるが、
    //以下のtoString()の処理をすることで、idとタイトルが見えるようになる。
    override fun toString(): String {
        return "id:$id, title:${articleTitle}"
    }
}