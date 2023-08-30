package com.bearya.monitor.center.data.bean

import com.google.gson.annotations.SerializedName

data class CustomerService(
    @SerializedName("server_times")
    val serverTimes: BaseNumber, // 客户服务次数
    @SerializedName("deal_rate")
    val dealRate: BaseNumber, // 问题解决率
    @SerializedName("portable_robot")
    val portableRobot: BaseNumber, // 便携机器人
    @SerializedName("desktop_robot")
    val desktopRobot: BaseNumber, // 桌面机器人
    @SerializedName("emergency_support")
    val emergencySupport: BaseNumber, //应急保障
    @SerializedName("consult_question")
    val consultQuestion: BaseQuestion, // 问题咨询 TOP5
    @SerializedName("servers")
    val servers: MutableList<BaseContent> // 售后服务
)

data class BaseQuestion(
    val name: String,
    val list: List<BaseCount>
)