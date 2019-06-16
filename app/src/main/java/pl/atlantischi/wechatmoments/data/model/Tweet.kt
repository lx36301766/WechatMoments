package pl.atlantischi.wechatmoments.data.model

import androidx.room.*
import pl.atlantischi.wechatmoments.data.db.TweetTypeConverter

@Entity(tableName = "tweets")
@TypeConverters(TweetTypeConverter::class)
class Tweet {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0

    var content: String? = null

    var images: Array<Image?>? = null

    @Embedded
    var sender: Sender? = null

    var error: String? = null //unknown error

    var comments: Array<Comment?>? = null

    class Image {
        var url: String? = null
    }

    class Sender {
        var username: String? = null
        var nick: String? = null
        var avatar: String? = null
    }

    class Comment {
        var content: String? = null
        var sender: Sender? = null
    }

}