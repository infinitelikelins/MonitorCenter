package com.bearya.monitor.center.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bearya.monitor.center.data.bean.CustomerService
import com.bearya.monitor.center.http.DataUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class CustomerServiceViewModel : ViewModel() {

    val customerService: LiveData<CustomerService?> = liveData(Dispatchers.IO) {
        repeat(1000000) {
            val data = DataUrl.customerService()?.data
            emit(data)
            delay(1000 * 40)
        }
    }

}