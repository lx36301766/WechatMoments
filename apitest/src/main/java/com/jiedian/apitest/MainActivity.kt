package com.jiedian.apitest

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rxhttp.wrapper.converter.FastJsonConverter
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.parse.SimpleParser
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RxHttp.setConverter(FastJsonConverter.create())

        val s = "{\"header\":{\"api\":\"YDeviceUpgrade.getUpgradeInfoForBigCabinet\",\"msg_id\":\"1024\",\"service\":\"sharedCharging\",\"userAgent\":\"okhttp\\/3.14.1\",\"ip\":\"192.168.15.230\",\"lang\":\"zh_CN\",\"type\":\"\",\"cookie\":\"\",\"code\":\"0\",\"action\":\"\",\"message\":\"success\",\"url\":\"\",\"server_time\":\"1582209052\",\"distinct_id\":\"\"},\"body\":{\"app\":{\"device_id\":\"30746dfc-90a5-468c-8a0c-a3b3351a58c4\",\"software_version\":\"1.090\",\"url\":\"http:\\/\\/app.int.jumei.com:8180\\/cabinet1089\\/2020119\\/feature_W1901_app_1089_1_19_11_14.apk\",\"md5\":\"fe29615dc8e0098d014c3a8beb7d571d\",\"upgrade_target\":1,\"upgrade_model\":\"2\",\"upgrade_time\":\"1582102225\",\"upgrade_type\":1,\"incr_md5\":\"\",\"incr_pkg_url\":\"\",\"trigger_time_min\":\"12:00\",\"trigger_time_max\":\"22:59\"},\"firmware\":\"\"},\"extra\":\"\"}"

        try {
            val gsonRes = Gson().fromJson<UpgradeResInfo>(s, UpgradeResInfo::class.java)
            Log.d(TAG, gsonRes.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            val fastjsonRes = JSON.parseObject(s, UpgradeResInfo::class.java)
            Log.d(TAG, fastjsonRes.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun rxUpgrade(v: View) {
        val entity = ApiRequestEntity<UpgradeReqInfo>()
        entity.header = RequestHeader("sharedCharging", "YDeviceUpgrade.getUpgradeInfoForBigCabinet")
        entity.header.msg_id = "1024"
        entity.body = UpgradeReqInfo("30746dfc-90a5-468c-8a0c-a3b3351a58c4", "1.089", Build.VERSION.INCREMENTAL)
        RxHttp.postObjectJson("https://sharechargapi.jmstatic.com/index.php")
//            .addAll(JSON.toJSONString(entity))
            .addObject(entity)
//            .asString()
            .asParser(SimpleParser.get(UpgradeResInfo::class.java))
            .subscribe({ info ->
                Log.d(TAG, info.toString())
//                if (info?.header == null || "1024" != info.header.msg_id || info.body == null) {
//                    JLog.w(TAG, "info null")
//                } else {
//                    if (info.body.app != null) {
//                        download(info.body.app)
//                    }
//                }
            }, { e ->
                Log.w(TAG, e)
            })
    }

    fun retrofitUpgrade(v: View) {
        val entity = ApiRequestEntity<UpgradeReqInfo>()
        entity.header = RequestHeader("sharedCharging", "YDeviceUpgrade.getUpgradeInfoForBigCabinet")
        entity.header.msg_id = "1024"
        entity.body = UpgradeReqInfo("30746dfc-90a5-468c-8a0c-a3b3351a58c4", "1.089", Build.VERSION.INCREMENTAL)
        val apiService = ApiClient.getInstance().retrofit.create(ApiService::class.java)
        val upgradeResInfoCall = apiService.upgradeDeviceInfo(entity)
        upgradeResInfoCall.enqueue(object : Callback<UpgradeResInfo> {
            override fun onResponse(call: Call<UpgradeResInfo>, response: Response<UpgradeResInfo>?) {
                Log.d(TAG, "response=$response")
                if (response?.body() != null) {
                    Log.d(TAG, "body=" + response.body())
                    val info = response.body()
                    if (info?.header == null || "1024" != info.header.msg_id || info.body == null) {
                        Log.w(TAG, "API获取升级消息：返回的消息Id不一致或者消息体为空")
                        return
                    }
                } else {
                    Log.e(TAG, "API获取的消息值为空！")
                }
            }

            override fun onFailure(call: Call<UpgradeResInfo>, t: Throwable) {
                Log.e(TAG, "请求API抛出异常:" + t.message)
            }
        })
    }

}
