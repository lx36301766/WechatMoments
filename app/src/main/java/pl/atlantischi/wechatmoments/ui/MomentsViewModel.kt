package pl.atlantischi.wechatmoments.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import pl.atlantischi.wechatmoments.MomentsApplication
import pl.atlantischi.wechatmoments.R
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
            R.mipmap.profile_default
            userInfo.value = repository.getUserInfo()
        }, {
            MomentsApplication.context.toast(it.message ?: "")
        })
    }

    fun getTweetList(page: Int) {
        launch({
            val result = repository.requestTweets(page)
            tweetList.value = result
        }, {
            MomentsApplication.context.toast(it.message ?: "")
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
