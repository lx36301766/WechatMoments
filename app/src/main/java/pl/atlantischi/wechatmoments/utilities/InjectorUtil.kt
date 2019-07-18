package pl.atlantischi.wechatmoments.utilities

import pl.atlantischi.wechatmoments.data.MomentsRepository
import pl.atlantischi.wechatmoments.data.db.MomentsDao
import pl.atlantischi.wechatmoments.data.network.MomentsNetwork
import pl.atlantischi.wechatmoments.ui.MomentsViewModelFactory

object InjectorUtil {

    private fun getMomentsRepository(): MomentsRepository {
        return MomentsRepository.getInstance(MomentsDao(), MomentsNetwork.getInstance())
    }

    fun provideMomentsViewModelFactory(): MomentsViewModelFactory {
        val repository = getMomentsRepository()
        return MomentsViewModelFactory(repository)
    }

}
