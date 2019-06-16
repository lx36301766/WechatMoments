package pl.atlantischi.wechatmoments.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import pl.atlantischi.wechatmoments.data.model.Tweet

@Dao
interface TweetDao {

    @Query("SELECT * FROM tweets")
    fun getCachedTweets(): List<Tweet>?

    @Insert(onConflict = REPLACE)
    fun putCachedTweets(tweet: List<Tweet>?)

}
