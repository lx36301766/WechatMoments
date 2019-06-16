package pl.atlantischi.wechatmoments.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.atlantischi.wechatmoments.data.MomentsRepository

/**
 * Created on 2019-06-16.

 * @author lx
 */


class MomentsViewModelFactory(private val repository: MomentsRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MomentsViewModel(repository) as T
    }
}