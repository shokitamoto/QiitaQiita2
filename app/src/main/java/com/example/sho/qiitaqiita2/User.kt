package com.example.sho.qiitaqiita2


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "description")
    var description: String,
    @Json(name = "facebook_id")
    var facebookId: String,
    @Json(name = "followees_count")
    var followeesCount: Int,
    @Json(name = "followers_count")
    var followersCount: Int,
    @Json(name = "github_login_name")
    var githubLoginName: Any?,
    @Json(name = "id")
    var id: String,
    @Json(name = "items_count")
    var itemsCount: Int,
    @Json(name = "linkedin_id")
    var linkedinId: String,
    @Json(name = "location")
    var location: String,
    @Json(name = "name")
    var name: String = "", // 初期値を設定するとnull回避。ふとした時にアプリ上でnull表示してしまうことを防ぐ
    @Json(name = "organization")
    var organization: String,
    @Json(name = "permanent_id")
    var permanentId: Int,
    @Json(name = "profile_image_url")
    var profileImageUrl: String,
    @Json(name = "team_only")
    var teamOnly: Boolean,
    @Json(name = "twitter_screen_name")
    var twitterScreenName: String,
    @Json(name = "website_url")
    var websiteUrl: String
){
    var displayName: String = "" // TODO:データベースに保存しないようにする //TODOorFIXME
        get() = when {
            //優先度順
            name.isNotEmpty() -> name
            githubLoginName != null && githubLoginName is String && (githubLoginName as String).isNotEmpty() -> "$githubLoginName"
            twitterScreenName.isNotEmpty() -> twitterScreenName
            else -> "NoName"
        }
}