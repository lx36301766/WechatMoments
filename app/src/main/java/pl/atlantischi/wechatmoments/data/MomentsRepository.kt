package pl.atlantischi.wechatmoments.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.atlantischi.wechatmoments.data.db.MomentsDao
import pl.atlantischi.wechatmoments.data.model.Tweet
import pl.atlantischi.wechatmoments.data.model.UserInfo
import pl.atlantischi.wechatmoments.data.network.MomentsNetwork

class MomentsRepository private constructor(private val momentsDao: MomentsDao, private val network: MomentsNetwork) {

    private var allTweetData: List<Tweet>? = null

    private val pageCount = 5

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

    suspend fun requestTweets(page: Int) = withContext(Dispatchers.IO) {
        if (allTweetData == null) {
            allTweetData = network.fetchTweets().filter {
                if (it.content == null && (it.images == null || it.images?.size == 0)) {
                    return@filter false
                }
                return@filter true
            }
        }
        val totalCount = allTweetData?.size ?: 0
        var start = pageCount * page
        var end = start + pageCount
        if (start >= totalCount) {
            start = totalCount
            end = totalCount
        } else if (end > totalCount) {
            end = totalCount
        }
        return@withContext allTweetData?.subList(start, end)
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