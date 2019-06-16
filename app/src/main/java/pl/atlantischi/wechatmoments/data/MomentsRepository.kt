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
        if (userInfo == null) userInfo = requestUserInfo()
        return userInfo
    }

    private suspend fun requestUserInfo() = withContext(Dispatchers.IO) {
        val userInfo = network.fetchUserInfo()
        momentsDao.cacheUserInfo(userInfo)
        userInfo
    }

    suspend fun getTweets(): MutableList<Tweet> {
        var tweets = momentsDao.getCachedTweets()
        if (tweets == null) tweets = requestTweets()
        return tweets
    }

    private suspend fun requestTweets() = withContext(Dispatchers.IO) {
        return@withContext network.fetchTweets().apply {
            momentsDao.cacheTweets(this)
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