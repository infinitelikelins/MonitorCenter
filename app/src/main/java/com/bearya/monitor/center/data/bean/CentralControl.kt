package com.bearya.monitor.center.data.bean

import com.google.gson.annotations.SerializedName

data class CentralControl(

    val partners: BaseNumber,

    @SerializedName("deployed_robots")
    val deployedRobots: BaseNumber,

    @SerializedName("course_sections")
    val courseSections: BaseNumber,

    @SerializedName("course_section_list")
    val courseSectionList: CourseSection,

    @SerializedName("course_section_hourse")
    val courseSectionHour: CourseSection,

    @SerializedName("course_section_duration")
    val courseSectionDuration: CourseDuration,

    @SerializedName("robot_log")
    val robotLog: List<RobotLog>
)

data class CourseDuration(
    val name: String,
    val list: List<BaseCount>
)

data class CourseSection(
    val id: Int,
    val name: String,
    val list: List<BaseCount>
)

data class RobotLog(
    val id: Int,
    val title: String,
    val code: String,
    val address: String,
    val online: Long,
    val createAt: Long,
    val status: Int
)
