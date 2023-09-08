package com.bearya.monitor.center.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.bearya.monitor.center.data.bean.LessonTrain
import com.bearya.monitor.center.data.bean.TrainLog
import com.bearya.monitor.center.http.DataUrl
import com.bearya.monitor.center.library.ext.setData
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class LessonTrainViewModel : ViewModel() {

    private val position: MutableLiveData<Int> = MutableLiveData<Int>(1)
    private val kv: MMKV by lazy { MMKV.defaultMMKV() }

    val lessonTrain: LiveData<LessonTrain?> = liveData(Dispatchers.IO) {
        repeat(Int.MAX_VALUE) { _->
            DataUrl.lessonTrain()?.data?.also { emit(it) }
            val delayMillis = kv.decodeInt("interface", 30) * 1000L + 5000L
            delay(delayMillis)
        }
    }

    val trainLog: LiveData<TrainLog?> = position.switchMap { pos ->
        liveData(Dispatchers.IO) {
            DataUrl.trainLog(pos)?.data?.also { emit(it) }
        }
    }

    private val count: LiveData<Int> = trainLog.switchMap { liveData { emit(it?.count ?: 0) } }

    val currentPageIndex: LiveData<String> = MediatorLiveData<String>().apply {
        addSource(count) { setData("第 ${position.value} 页 , 共 ${count.value} 页") }
        addSource(position) { setData("第 ${position.value} 页 , 共 ${count.value} 页") }
    }

    fun next() {
        if (((count.value ?: 0) > (position.value ?: 0))) {
            position.value = position.value?.plus(1)
        } else position.value = 1
    }

    fun up() {
        if ((position.value ?: 0) > 1) {
            position.value = position.value?.minus(1)
        }
    }

}