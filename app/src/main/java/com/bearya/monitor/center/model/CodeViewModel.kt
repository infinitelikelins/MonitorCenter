package com.bearya.monitor.center.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bearya.monitor.center.http.ApiUrl
import com.bearya.monitor.center.library.ext.Empty
import com.bearya.monitor.center.library.ext.Fail
import com.bearya.monitor.center.library.ext.NoNet
import java.net.UnknownHostException

class CodeViewModel : ViewModel() {

    fun activateVerify(code: String?): LiveData<Int?> = liveData {
        when {
            code.isNullOrBlank() -> emit(Empty)
            else -> emit(try {
                ApiUrl.activateVerify(code)?.data
            } catch (ex: UnknownHostException) {
                NoNet
            } catch (ex: Exception) {
                Fail
            })
        }
    }


}

