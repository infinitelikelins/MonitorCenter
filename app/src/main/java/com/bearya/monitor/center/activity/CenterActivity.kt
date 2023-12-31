package com.bearya.monitor.center.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bearya.monitor.center.databinding.ActivityCenterBinding
import com.bearya.monitor.center.fragment.CentralControlFragment
import com.bearya.monitor.center.fragment.CustomerServiceFragment
import com.bearya.monitor.center.fragment.LessonTrainFragment
import com.bearya.monitor.center.model.CenterViewModel
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CenterActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, CenterActivity::class.java))
        }
    }

    private lateinit var bindView: ActivityCenterBinding
    private val kv: MMKV by lazy { MMKV.defaultMMKV() }

    private val viewModel: CenterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView = ActivityCenterBinding.inflate(layoutInflater)
        setContentView(bindView.root)

        bindView.fragmentContainer.adapter = object : FragmentStateAdapter(this) {

            private val centralControlFragment: CentralControlFragment by lazy { CentralControlFragment() }
            private val customerServiceFragment: CustomerServiceFragment by lazy { CustomerServiceFragment() }
            private val lessonTrainFragment: LessonTrainFragment by lazy { LessonTrainFragment() }

            override fun getItemCount(): Int = 3

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> centralControlFragment
                    1 -> customerServiceFragment
                    else -> lessonTrainFragment
                }
            }

        }

        bindView.fragmentContainer.offscreenPageLimit = 3

        viewModel.playIndex.observe(this) {
            bindView.fragmentContainer.setCurrentItem(it, true)
        }

    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            repeat(Int.MAX_VALUE) {
                val delayMillis = kv.decodeInt("switch", 1) * 60 * 1000L
                delay(delayMillis)
                val auto = kv.decodeBool("auto")
                if (auto) {
                    viewModel.next()
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (event.keyCode) {
            KeyEvent.KEYCODE_MENU, KeyEvent.KEYCODE_M -> SettingActivity.start(this@CenterActivity)
            KeyEvent.KEYCODE_DPAD_RIGHT -> viewModel.next()
            KeyEvent.KEYCODE_DPAD_LEFT -> viewModel.up()
            KeyEvent.KEYCODE_DPAD_DOWN -> viewModel.onKeyListener?.invoke(KeyEvent.KEYCODE_DPAD_DOWN)
            KeyEvent.KEYCODE_DPAD_UP -> viewModel.onKeyListener?.invoke(KeyEvent.KEYCODE_DPAD_UP)
        }
        return super.onKeyDown(keyCode, event)
    }

}