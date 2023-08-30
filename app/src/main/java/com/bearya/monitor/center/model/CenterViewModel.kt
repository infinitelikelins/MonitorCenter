package com.bearya.monitor.center.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CenterViewModel : ViewModel() {

    val playIndex: MutableLiveData<Int> = MutableLiveData<Int>(0)
    var onKeyListener : ((Int) -> Unit)? = null

    fun next() {
        if (playIndex.value == 0) playIndex.value = 1
        else if (playIndex.value == 1) playIndex.value = 2
        else playIndex.value = 0
    }

    fun up() {
        if (playIndex.value == 0) playIndex.value = 2
        else if (playIndex.value == 1) playIndex.value = 0
        else playIndex.value = 1
    }

}