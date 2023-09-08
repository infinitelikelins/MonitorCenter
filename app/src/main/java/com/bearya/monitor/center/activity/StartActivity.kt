package com.bearya.monitor.center.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bearya.monitor.center.BuildConfig
import com.bearya.monitor.center.databinding.ActivityStartBinding
import com.bearya.monitor.center.library.ext.VERIFY_RESULT
import com.tencent.mmkv.MMKV

class StartActivity : AppCompatActivity() {

    private lateinit var bindView: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView = ActivityStartBinding.inflate(layoutInflater)
        setContentView(bindView.root)

        if (MMKV.defaultMMKV().decodeBool(VERIFY_RESULT, BuildConfig.DEBUG)) {
            CenterActivity.start(this@StartActivity)
            finish()
        }

    }

}