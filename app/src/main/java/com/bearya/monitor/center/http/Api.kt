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
    suspend fun activateVerify(code: String = "0"): HttpResult<Int>? =
        try { api?.activateVerify(code) }catch (e: Exception) { null }

}

object DataUrl : HttpRetrofit(BuildConfig.DataUrl) {

    private val api: DataService? by lazy(mode = LazyThreadSafetyMode.NONE) {
        retrofit.create(DataService::class.java)
    }

    suspend fun customerService(): HttpResult<CustomerService>? =
        try { api?.customerService() } catch (e: Exception) { null }

    suspend fun lessonTrain(): HttpResult<LessonTrain>? =
        try { api?.lessonTrain() } catch (e: Exception) { null }

    suspend fun centralControl(): HttpResult<CentralControl>? =
        try { api?.centralControl() } catch (e: Exception) { null }

    suspend fun trainLog(pageIndex: Int): HttpResult<TrainLog>? =
        try { api?.trainLog(pageIndex, 15) } catch (e: Exception) { null }


}