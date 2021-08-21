package com.example.sho.qiitaqiita2


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tag(
    @Json(name = "name")
    var name: String,
    @Json(name = "versions")
    var versions: List<Any>
)