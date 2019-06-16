package pl.atlantischi.wechatmoments.data.db

import androidx.room.TypeConverter
import pl.atlantischi.wechatmoments.data.model.Tweet
import com.squareup.moshi.Types
import pl.atlantischi.wechatmoments.data.network.ServiceCreator


/**
 * Created on 20/09/2018.
 *
 * @author lx
 */

class TweetTypeConverter {

    val commentAdapter = ServiceCreator.moshi.adapter<Array<Tweet.Comment?>>(Types.newParameterizedType(List::class.java, Tweet.Comment::class.java))

    @TypeConverter
    fun commentsToString(comments: Array<Tweet.Comment?>?) = commentAdapter.toJson(comments)

    @TypeConverter
    fun stringToComments(str: String) = commentAdapter.fromJson(str)


    val imageAdapter = ServiceCreator.moshi.adapter<Array<Tweet.Image?>>(Types.newParameterizedType(List::class.java, Tweet.Image::class.java))

    @TypeConverter
    fun imageArrayToString(list: Array<Tweet.Image?>) = imageAdapter.toJson(list)

    @TypeConverter
    fun stringToImageArray(str: String) = imageAdapter.fromJson(str)



    val stringAdapter = ServiceCreator.moshi.adapter<Array<String?>>(Types.newParameterizedType(List::class.java, String::class.java))

    @TypeConverter
    fun arrayToString(list: Array<String?>) = stringAdapter.toJson(list)

    @TypeConverter
    fun stringToArray(str: String) = stringAdapter.fromJson(str)
}
