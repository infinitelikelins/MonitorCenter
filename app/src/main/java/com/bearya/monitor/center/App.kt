package com.bearya.monitor.center

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV

class App :Application() {

    override fun onCreate() {
        super.onCreate()
        CrashReport.initCrashReport(this, "f516764175", true)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        Logger.addLogAdapter(object : AndroidLogAdapter(
            PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .methodCount(5)
                .tag("LOGGER")
                .build()
        ) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
        MMKV.initialize(this)
    }

}