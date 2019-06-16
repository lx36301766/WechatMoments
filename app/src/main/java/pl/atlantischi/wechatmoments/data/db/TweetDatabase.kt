package pl.atlantischi.wechatmoments.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.atlantischi.wechatmoments.data.model.Tweet

/**
 * Created on 2019-06-16.

 * @author lx
 */

@Database(entities = [(Tweet::class)], version = 1, exportSchema = false)
abstract class TweetDatabase : RoomDatabase() {

    abstract fun getTweetDao(): TweetDao

    companion object {

        @Volatile
        private var instance: TweetDatabase? = null

        fun getInstance(context: Context): TweetDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context.applicationContext, TweetDatabase::class.java, "tweets.db").build()
            }
        }

    }


}
