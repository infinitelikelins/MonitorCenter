package com.bearya.monitor.center.library.ext

import android.os.Looper
import androidx.lifecycle.MutableLiveData

/**
 * 线程分配设置数据到LiveData
 */
fun <T> MutableLiveData<T>.setData(data: T?) = apply {
    if (Looper.getMainLooper().thread == Thread.currentThread()) {
        setValue(data)
    } else {
        postValue(data)
    }
}

var <T> MutableLiveData<T?>.data: T?
    set(value) {
        if (Looper.getMainLooper().thread == Thread.currentThread())
            setValue(value)
        else postValue(value)
    }
    get() {
        return null
    }

