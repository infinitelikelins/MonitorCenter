package com.bearya.monitor.center.http

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

internal interface ApiService {

    /**
     * 验证码激活
     */
    @POST("/v2/material/tvCode/active")
    @FormUrlEncoded
    suspend fun activateVerify(@Field("code") code: String): HttpResult<Int>


}