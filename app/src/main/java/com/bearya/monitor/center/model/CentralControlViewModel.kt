package com.bearya.monitor.center.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bearya.monitor.center.data.bean.CentralControl
import com.bearya.monitor.center.http.DataUrl
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class CentralControlViewModel : ViewModel() {

    private val kv: MMKV by lazy { MMKV.defaultMMKV() }

    val centralControl: LiveData<CentralControl?> = liveData(Dispatchers.IO) {
        repeat(Int.MAX_VALUE) { _->
            DataUrl.centralControl()?.data?.also { emit(it) }
            val delayMillis = kv.decodeInt("interface", 30) * 1000L
            delay(delayMillis)
        }
    }

}