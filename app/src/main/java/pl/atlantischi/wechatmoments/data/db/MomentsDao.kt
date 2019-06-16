package pl.atlantischi.wechatmoments.data.db

import android.preference.PreferenceManager
import androidx.core.content.edit
import pl.atlantischi.wechatmoments.MomentsApplication
import pl.atlantischi.wechatmoments.data.model.Tweet
import pl.atlantischi.wechatmoments.data.model.UserInfo
import pl.atlantischi.wechatmoments.data.network.ServiceCreator


class MomentsDao {

    private val userInfoAdapter = ServiceCreator.moshi.adapter(UserInfo::class.java)

    fun getCachedUserInfo(): UserInfo? {
        val userInfo = PreferenceManager.getDefaultSharedPreferences(MomentsApplication.context).getString("userInfo", null)
        if (userInfo != null) {
            return userInfoAdapter.fromJson(userInfo)
        }
        return null
    }

    fun cacheUserInfo(userInfo: UserInfo) {
        PreferenceManager.getDefaultSharedPreferences(MomentsApplication.context).edit {
            putString("userInfo", userInfoAdapter.toJson(userInfo))
        }
    }

    private val tweetDao = TweetDatabase.getInstance(MomentsApplication.context).getTweetDao()

    fun getCachedTweets() = tweetDao.getCachedTweets()

    fun cacheTweets(tweets: List<Tweet>) = tweetDao.putCachedTweets(tweets)

}
