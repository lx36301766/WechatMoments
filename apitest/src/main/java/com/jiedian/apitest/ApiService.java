package com.jiedian.apitest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * AUTHOR：lanchuanke on 17/11/11 16:39
 */
public interface ApiService {

    @POST("index.php")
    Call<UpgradeResInfo> upgradeDeviceInfo(@Body ApiRequestEntity entity);

}
