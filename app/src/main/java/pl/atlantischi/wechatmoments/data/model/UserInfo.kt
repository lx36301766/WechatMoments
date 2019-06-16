package pl.atlantischi.wechatmoments.data.model

import com.squareup.moshi.Json

class UserInfo {

    @Json(name = "profile_image")
    var profileImage: String? = null

    var avatar: String? = null

    var nick: String? = null

    var username: String? = null

}
