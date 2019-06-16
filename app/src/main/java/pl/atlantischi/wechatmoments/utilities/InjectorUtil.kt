package pl.atlantischi.wechatmoments.utilities

import android.content.Context
import pl.atlantischi.wechatmoments.data.MomentsRepository
import pl.atlantischi.wechatmoments.data.db.MomentsDao
import pl.atlantischi.wechatmoments.data.network.MomentsNetwork
import pl.atlantischi.wechatmoments.ui.MomentsViewModelFactory

object InjectorUtil {

    private fun getMomentsRepository(context: Context): MomentsRepository {
        return MomentsRepository.getInstance(MomentsDao(), MomentsNetwork.getInstance())
    }

    fun provideMomentsViewModelFactory(context: Context): MomentsViewModelFactory {
        val repository = getMomentsRepository(context)
        return MomentsViewModelFactory(repository)
    }

}
