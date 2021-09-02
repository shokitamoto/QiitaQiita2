package com.example.sho.qiitaqiita2

import com.squareup.moshi.Json
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Favorite : RealmObject() (
    @PrimaryKey
    var id : String = "", // 記事ID
    var articleTitle : String = "", // タイトル
    var articleContent : String ="" // 記事文章

    // var deletedAt: Date? = null // 削除された日付。null <- 削除されていない。!null <- 削除された
    // createdAt: レコードが作られた日時
    // updatedAt: レコードが更新された日時
    // deletedAt: レコードが削除された日時

    companion object {
    // ここに書くと、インスタンスではなくて、クラスの変数・定数・メソッドが書ける。

        fun findAll(): List<Favorite> { // お気に入りを全取得する 1件もなければ空のリストを返す。
            return Realm.getDefaultInstance().use { realm ->
                realm.where(Favorite::class.java)
                    .findAll()
                    .let {
                        realm.copyFromRealm(it)
                    }
            }
        }

        // お気に入り状態を代入することに使う
        fun findBy(id: String): Favorite? { //idと一致するものを返す。なければnull
            return Realm.getDefaultInstance().use { realm ->
                realm.where(Favorite::class.java)
                    .equalTo("id", id) //
                    .findFirst()
                    ?.let {
                        realm.copyFromRealm(it)
                    }
            }
        }

        fun insert(favorite: Favorite) { // insert:レコードを追加する1回目は成功するが、2回目はエラーになる（プライマリーキーで判断される） update:レコードの更新。プライマリー木がない場合は追加。あれば更新してくれる
            Realm.getDefaultInstance().use { realm ->
                realm.executeTransaction{
                    it.insertOrUpdate(favorite) // insertOrUpdate
                }
            }
        }
        fun delete(deleteTargetId: String) {
            Realm.getDefaultInstance().use { realm ->
                realm.where(Favorite::class.java) //Favorite::articleTitle.name -> "articleTitle"
                    .equalTo(Favorite::id.name, deleteTargetId) //nameをつくけることで、変数名を取得する
                    .findFirst()
                    ?.also { favorite ->
                        realm.executeTransaction {
                            favorite.deleteFromRealm() // 物理削除
                        }
                    }
                }
            }
        }
    }
)

