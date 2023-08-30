package com.bearya.monitor.center.library.ext

import android.content.Context

//================================== 课件需要激活的验证结果的状态常量
/**
 * 没有联网，不可验证激活码
 */
const val NoNet: Int = -2
/**
 * 激活码为空
 */
const val Empty: Int = -1
/**
 * 多种因素验证失败
 */
const val Fail: Int = 0
/**
 * 激活码验证正确
 */
const val Success: Int = 1
/**
 * 该激活码已失效
 */
const val Invalid: Int = 2
/**
 * 该激活码不存在
 */
const val None = 3
//==================================

fun Int?.dp2px(context: Context): Int? = this?.times(context.resources.displayMetrics.density)?.plus(0.5f)?.toInt()