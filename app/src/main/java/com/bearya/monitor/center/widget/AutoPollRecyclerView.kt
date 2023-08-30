package com.bearya.monitor.center.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

class AutoPollRecyclerView : RecyclerView {

    var autoPollTask: AutoPollTask? = AutoPollTask(this) //滚动线程

    private var running //是否正在滚动
            = false
    private var canRun //是否可以自动滚动，根据数据是否超出屏幕来决定
            = false

    private val scrollListener: OnScrollListener = object : OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (computeVerticalScrollExtent() + computeVerticalScrollOffset() >= computeVerticalScrollRange()) {
                scrollToPosition(0)
            }
        }
    }

    constructor(context: Context) : super(context) {
        addOnScrollListener(scrollListener)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        addOnScrollListener(scrollListener)
    }

    inner class AutoPollTask(reference: AutoPollRecyclerView?) : Runnable {
        private val mReference: WeakReference<AutoPollRecyclerView> = WeakReference<AutoPollRecyclerView>(reference)
        override fun run() {
            val recyclerView: AutoPollRecyclerView? = mReference.get() //获取recyclerview对象
            if (recyclerView != null && recyclerView.running && recyclerView.canRun) {
                recyclerView.scrollBy(1, 1) //注意scrollBy和scrollTo的区别
                //延迟发送
                recyclerView.postDelayed(recyclerView.autoPollTask, delayTime)
            }
        }
    }

    //开启:如果正在运行,先停止->再开启
    fun start() {
        if (running) stop()
        canRun = true
        running = true
        postDelayed(autoPollTask, delayTime)
    }

    fun stop() {
        running = false
        removeCallbacks(autoPollTask)
    }

    companion object {
        private const val delayTime: Long = 40 //间隔多少时间后执行滚动
    }
}