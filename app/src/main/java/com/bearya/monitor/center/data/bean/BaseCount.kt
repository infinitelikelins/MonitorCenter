package com.bearya.monitor.center.data.bean

import com.google.gson.annotations.SerializedName

data class BaseCount(
    val name: String,
    val num: Int,
    val id: Int,
    @SerializedName("tag_id")
    val tagId: Int,
    val tag: String
)
