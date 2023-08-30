package com.bearya.monitor.center.http

import com.bearya.monitor.center.BuildConfig
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class HttpRetrofit protected constructor(private val url :String) {

    private val okHttpClient: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }

        val cookieJar : CookieJar = object : CookieJar {
            private val cookieStore : HashMap<String, List<Cookie>> = HashMap()
            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                return cookieStore[url.host] ?: emptyList()
            }
            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                cookieStore[url.host] = cookies
            }
        }

        OkHttpClient.Builder()
                .connectTimeout(5 * 1000L, TimeUnit.MILLISECONDS)
                .readTimeout(5 * 1000L, TimeUnit.MILLISECONDS)
                .callTimeout(5 * 1000L, TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                .cookieJar(cookieJar)
                .build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

}

data class HttpResult<T>(
        /** 服务器返回结果状态码  */
        var status: Int,
        /** 服务器返回的具体业务数据，为了统一处理，此处是指成功返回的结果模型  */
        var data: T?,
        /** 服务器信息  */
        var text: String,
        /** 状态码 */
        var status_code: Int?) {

    override fun toString(): String {
        return "HttpResult = { status=${status} , data=${data} , text=${text} , statusCode=${status_code}'}"
    }

}