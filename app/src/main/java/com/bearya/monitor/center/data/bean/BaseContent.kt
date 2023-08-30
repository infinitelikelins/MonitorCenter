package com.bearya.monitor.center.data.bean

import com.google.gson.annotations.SerializedName

data class BaseContent(
    val id: Int,
    val title: String,
    val city: String,
    val type: Int,
    val status: Int,
    @SerializedName("create_at")
    val createAt: Long,
    @SerializedName("is_delete")
    val isDelete: Int,
    val dateline: Long,
    val province: String
)