package pl.atlantischi.wechatmoments.data.network

import pl.atlantischi.wechatmoments.data.network.api.MomentsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MomentsNetwork {

    private val weatherService = ServiceCreator.create(MomentsService::class.java)

    suspend fun fetchTweets() = weatherService.getTweets().await()

    suspend fun fetchUserInfo() = weatherService.getUserInfo().await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }

    companion object {

        private var network: MomentsNetwork? = null

        fun getInstance(): MomentsNetwork {
            if (network == null) {
                synchronized(MomentsNetwork::class.java) {
                    if (network == null) {
                        network = MomentsNetwork()
                    }
                }
            }
            return network!!
        }

    }

}