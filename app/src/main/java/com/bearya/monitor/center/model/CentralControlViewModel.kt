package com.bearya.monitor.center.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bearya.monitor.center.data.bean.CentralControl
import com.bearya.monitor.center.http.DataUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class CentralControlViewModel : ViewModel() {

    val centralControl: LiveData<CentralControl?> = liveData(Dispatchers.IO) {
        repeat(1000000) {
            val data = DataUrl.centralControl()?.data
            emit(data)
            delay(1000 * 35)
        }
    }

}