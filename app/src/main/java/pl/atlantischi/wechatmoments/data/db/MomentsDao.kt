package pl.atlantischi.wechatmoments.data.db

import android.preference.PreferenceManager
import androidx.core.content.edit
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import pl.atlantischi.wechatmoments.MomentsApplication
import pl.atlantischi.wechatmoments.data.model.Tweet
import pl.atlantischi.wechatmoments.data.model.UserInfo


class MomentsDao {

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val userInfoAdapter = moshi.adapter(UserInfo::class.java)

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

    fun getCachedTweets(): MutableList<Tweet>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun cacheTweets(tweets: MutableList<Tweet>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}