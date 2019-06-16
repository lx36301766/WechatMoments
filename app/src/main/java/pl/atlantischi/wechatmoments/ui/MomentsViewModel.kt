package pl.atlantischi.wechatmoments.ui

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.atlantischi.wechatmoments.MomentsApplication
import pl.atlantischi.wechatmoments.data.MomentsRepository
import pl.atlantischi.wechatmoments.data.model.Tweet
import pl.atlantischi.wechatmoments.data.model.UserInfo

/**
 * Created on 2019-06-16.

 * @author lx
 */

class MomentsViewModel(private val repository: MomentsRepository) : ViewModel() {

    var userInfo = MutableLiveData<UserInfo>()

    var tweetList = MutableLiveData<List<Tweet>>()

    fun getUserInfo() {
        launch({
            userInfo.value = repository.getUserInfo()
        }, {
            Toast.makeText(MomentsApplication.context, it.message, Toast.LENGTH_SHORT).show()
        })
    }

    fun getTweetList() {
        launch({
            val result = repository.requestTweets()
            tweetList.value = result

        }, {
            Toast.makeText(MomentsApplication.context, it.message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
        }
    }


}
