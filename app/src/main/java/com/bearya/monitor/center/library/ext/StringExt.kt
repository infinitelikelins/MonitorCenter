package com.bearya.monitor.center.library.ext

import android.os.Environment
import java.io.File

const val VERIFY_RESULT = "VERIFY"

fun String?.assetsPath(): String =  "file:///android_asset/$this"

fun String?.downloadPath(): String = "${Environment.getExternalStorageDirectory()}${File.separator}$this"

fun emotion(emotion: String?): String? = emotion?.takeIf { it.isNotBlank() }?.let { "emotion/${emotion}.json"  }
