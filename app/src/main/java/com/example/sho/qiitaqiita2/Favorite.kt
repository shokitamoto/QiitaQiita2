package com.example.sho.qiitaqiita2

import com.squareup.moshi.Json
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Favorite (
    @PrimaryKey var id : String = "", // 記事ID
    @Json(name = "title") var articleTitle : String = "", // タイトル
    @Json(name = "body") var articleContent : String ="" // 記事文章
) : RealmObject()