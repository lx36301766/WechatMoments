package pl.atlantischi.wechatmoments.data.network.api

import pl.atlantischi.wechatmoments.data.model.Tweet
import pl.atlantischi.wechatmoments.data.model.UserInfo
import retrofit2.Call
import retrofit2.http.GET

interface MomentsService {

    @GET("user/jsmith/tweets")
    fun getTweets(): Call<MutableList<Tweet>>

    @GET("user/jsmith")
    fun getUserInfo(): Call<UserInfo>

}
