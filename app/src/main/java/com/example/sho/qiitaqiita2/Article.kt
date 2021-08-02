package com.example.sho.qiitaqiita2

class Article {
    var id = "" // 記事ID
    var title = "" // タイトル

    //このままだとcom.example.sho.qiitaqiita2.Article@ajfeo234
    //のようにハッシュコードで表示されるが、
    //以下のtoString()の処理をすることで、idとタイトルが見えるようになる。
    override fun toString(): String {
        return "id:$id, title:$title"
    }
}