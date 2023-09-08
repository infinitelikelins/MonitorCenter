package com.bearya.monitor.center.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bearya.monitor.center.data.bean.CustomerService
import com.bearya.monitor.center.http.DataUrl
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class CustomerServiceViewModel : ViewModel() {

    private val kv: MMKV by lazy { MMKV.defaultMMKV() }

    val customerService: LiveData<CustomerService?> = liveData(Dispatchers.IO) {
        repeat(Int.MAX_VALUE) { _->
            DataUrl.customerService()?.data?.also { emit(it) }
            val delayMillis = kv.decodeInt("interface", 30) * 1000L + 3000L
            delay(delayMillis)
        }
    }

}