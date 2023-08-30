package com.bearya.monitor.center.data.bean

import com.google.gson.annotations.SerializedName

data class LessonTrain(
    @SerializedName("course_use_sum")
    val courseUseSum:CourseUse,
    @SerializedName("course_use_today")
    val courseUseToday :CourseUse
)

data class CourseUse(
    val name: String,
    val list: List<BaseCount>
)

data class TrainLog(
    val total:Int,
    val limit:Int,
    val count : Int,
    val pos:Int,
    val list: List<BaseContent>
)