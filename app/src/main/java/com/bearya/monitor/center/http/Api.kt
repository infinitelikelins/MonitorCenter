package com.bearya.monitor.center.http

import com.bearya.monitor.center.BuildConfig
import com.bearya.monitor.center.data.bean.CentralControl
import com.bearya.monitor.center.data.bean.CustomerService
import com.bearya.monitor.center.data.bean.LessonTrain
import com.bearya.monitor.center.data.bean.TrainLog

object ApiUrl : HttpRetrofit(BuildConfig.ApiUrl) {

    private val api: ApiService? by lazy(mode = LazyThreadSafetyMode.NONE) {
        retrofit.create(ApiService::class.java)
    }

    /**
     * 课件激活验证
     */
    suspend fun activateVerify(code: String = "0"): HttpResult<Int>? = api?.activateVerify(code)

}

object DataUrl : HttpRetrofit(BuildConfig.DataUrl) {

    private val api: DataService? by lazy(mode = LazyThreadSafetyMode.NONE) {
        retrofit.create(DataService::class.java)
    }

    suspend fun customerService(): HttpResult<CustomerService>? = api?.customerService()

    suspend fun lessonTrain(): HttpResult<LessonTrain>? = api?.lessonTrain()

    suspend fun centralControl(): HttpResult<CentralControl>? = api?.centralControl()

    suspend fun trainLog(pageIndex: Int): HttpResult<TrainLog>? = api?.trainLog(pageIndex ,15)

}