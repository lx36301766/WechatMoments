package pl.atlantischi.wechatmoments.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.atlantischi.wechatmoments.data.db.MomentsDao
import pl.atlantischi.wechatmoments.data.model.Tweet
import pl.atlantischi.wechatmoments.data.model.UserInfo
import pl.atlantischi.wechatmoments.data.network.MomentsNetwork

class MomentsRepository private constructor(private val momentsDao: MomentsDao, private val network: MomentsNetwork) {

    suspend fun getUserInfo(): UserInfo {
        var userInfo = momentsDao.getCachedUserInfo()
        if (userInfo == null) {
            userInfo = network.fetchUserInfo()
            momentsDao.cacheUserInfo(userInfo)
        }
        return userInfo
    }

    suspend fun getTweets(): List<Tweet> {
        var tweets = momentsDao.getCachedTweets()
        if (tweets == null) {
            tweets = network.fetchTweets()
            momentsDao.cacheTweets(tweets)
        }
        return tweets
    }

    suspend fun requestTweets() = withContext(Dispatchers.IO) {
        return@withContext network.fetchTweets().apply {
//            momentsDao.cacheTweets(this)
        }
    }

    companion object {

        private lateinit var instance: MomentsRepository

        fun getInstance(dao: MomentsDao, network: MomentsNetwork): MomentsRepository {
            if (!::instance.isInitialized) {
                synchronized(MomentsRepository::class.java) {
                    if (!::instance.isInitialized) {
                        instance = MomentsRepository(dao, network)
                    }
                }
            }
            return instance
        }

    }

}