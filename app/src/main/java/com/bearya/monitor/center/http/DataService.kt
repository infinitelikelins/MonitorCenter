package com.bearya.monitor.center.http

import com.bearya.monitor.center.data.bean.CentralControl
import com.bearya.monitor.center.data.bean.CustomerService
import com.bearya.monitor.center.data.bean.LessonTrain
import com.bearya.monitor.center.data.bean.TrainLog
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

internal interface DataService {

    @GET("/api/stat/custom")
    suspend fun customerService(): HttpResult<CustomerService>

    @GET("/api/stat/course")
    suspend fun lessonTrain(): HttpResult<LessonTrain>

    @GET("/api/stat/platform")
    suspend fun centralControl(): HttpResult<CentralControl>

    @POST("/api/stat/train/log")
    @FormUrlEncoded
    suspend fun trainLog(@Field("page") page: Int, @Field("pagesize") limit: Int): HttpResult<TrainLog>

}
