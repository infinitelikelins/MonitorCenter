package com.bearya.monitor.center.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatRadioButton
import com.bearya.monitor.center.R
import com.bearya.monitor.center.databinding.ActivitySettingBinding
import com.tencent.mmkv.MMKV

class SettingActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, SettingActivity::class.java))
        }
    }

    private lateinit var bindView: ActivitySettingBinding
    private val kv: MMKV by lazy { MMKV.defaultMMKV() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(bindView.root)

        onViewFocused(bindView.auto, bindView.manual)
        onViewFocused(bindView.interface30, bindView.interface60, bindView.interface180, bindView.interface300)
        onViewFocused(bindView.switch1, bindView.switch3, bindView.switch5, bindView.switch10)

        val paginationId = if (kv.decodeBool("auto", false)) R.id.auto else R.id.manual
        val switchFrequencyId = when (kv.decodeInt("switch", 1)) {
            1 -> R.id.switch_1
            3 -> R.id.switch_3
            5 -> R.id.switch_5
            10 -> R.id.switch_10
            else -> R.id.switch_1
        }
        val interfaceFrequencyId = when (kv.decodeInt("interface", 30)) {
            30 -> R.id.interface_30
            60 -> R.id.interface_60
            180 -> R.id.interface_180
            300 -> R.id.interface_300
            else -> R.id.interface_30
        }

        bindView.pagination.check(paginationId)
        bindView.switchFrequency.check(switchFrequencyId)
        bindView.interfaceFrequency.check(interfaceFrequencyId)

        bindView.pagination.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.auto -> kv.encode("auto", 1)
                R.id.manual -> kv.encode("auto", false)
            }
        }
        bindView.switchFrequency.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.switch_1 -> kv.encode("switch", 1)
                R.id.switch_3 -> kv.encode("switch", 3)
                R.id.switch_5 -> kv.encode("switch", 5)
                R.id.switch_10 -> kv.encode("switch", 10)
            }
        }
        bindView.interfaceFrequency.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.interface_30 -> kv.encode("interface", 30)
                R.id.interface_60 -> kv.encode("interface", 60)
                R.id.interface_180 -> kv.encode("interface", 180)
                R.id.interface_300 -> kv.encode("interface", 300)
            }
        }
    }

    private fun onViewFocused(vararg views: AppCompatRadioButton) {
        views.forEach {
            it.setOnFocusChangeListener { v, hasFocus ->
                val scale = if (hasFocus) 1.2f else 1.0f
                v.animate().scaleX(scale).scaleY(scale).start()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        bindView.auto.requestFocus()
    }

}