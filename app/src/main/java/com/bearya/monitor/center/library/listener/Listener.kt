package com.bearya.monitor.center.library.listener

import android.view.View

typealias OnItemSelectedListener<T> = (view: View, item: T?, position: Int) -> Unit