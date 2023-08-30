package com.bearya.monitor.center.library.tool

import android.content.Context
import android.media.MediaPlayer
import android.text.TextUtils
import java.lang.ref.WeakReference

object Music {

    private var localMusicPlayer: LocalMusicPlayer? = null

    private var audios: String? = null

    private lateinit var mContext: WeakReference<Context>

    private var isCompleted: Boolean = true

    fun init(context: Context) {
        mContext = WeakReference(context)
        mContext.get()?.apply {
            localMusicPlayer = LocalMusicPlayer(this, false)
        }
    }

    fun autoAudio(name: String? , listener: ((isPlaying:Boolean,message:String) -> Unit)? = null) {
        if (TextUtils.equals(audios, name) && !isCompleted) {
            if (localMusicPlayer?.isPlaying() == true) {
                localMusicPlayer?.pause()
                listener?.invoke(false,"暂停播放")
            } else {
                localMusicPlayer?.resume()
                listener?.invoke(true,"继续播放")
            }
        } else {
            try {
                audios = name
                localMusicPlayer?.stop()
                localMusicPlayer?.mOnCompletionListener = {
                    isCompleted = true
                    listener?.invoke(false,"播放完成")
                }
                localMusicPlayer?.isLooping = false
                localMusicPlayer?.play(audios)
                isCompleted = false
                listener?.invoke(true,"开始播放")
            } catch (ex: Exception) {
                isCompleted = true
                listener?.invoke(false,"播放异常 - ${ex.message}")
            }
        }
    }

    fun stop() {
        audios = null
        localMusicPlayer?.stop()
    }

    fun destroy() {
        localMusicPlayer?.release()
    }

}

private class LocalMusicPlayer(context: Context, looping: Boolean = false) {

    private var mContext: WeakReference<Context>? = null
    private var mp: MediaPlayer? = null

    var mOnCompletionListener: (() -> Unit)? = null
    var isLooping: Boolean = false
        set(value) {
            field = value
            mp?.isLooping = value
        }

    init {
        mContext = WeakReference(context)
        mp = MediaPlayer().apply {
            setOnCompletionListener { mOnCompletionListener?.invoke() }
            setOnErrorListener { _, _, _ -> true }
        }
        isLooping = looping
    }

    @Throws(Exception::class)
    private fun setDataSource(dataSource: String) {
        if (dataSource.isNotEmpty()) {
            mp?.setDataSource(dataSource)
        }
    }

    @Throws(Exception::class)
    fun play(ds: String?) {
        if (ds != null && ds.isNotEmpty()) {
            stop()
            setDataSource(ds)
            mp?.setOnPreparedListener { it.start() }
            mp?.prepareAsync()
        } else {
            throw Exception("dataSource is empty")
        }
    }

    fun stop() {
        pause()
        mp?.reset()
        mp?.isLooping = isLooping
    }

    fun release() {
        stop()
        mp?.release()
        mp = null
    }

    fun isPlaying() = mp?.isPlaying

    fun resume() = mp?.start()

    fun pause() = mp?.isPlaying?.takeIf { it }?.apply { mp?.pause() }

}