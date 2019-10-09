package com.github.pgycode.wanandroid.common

import okhttp3.OkHttpClient
import okhttp3.Request
import android.util.Log


/**
 * @author: xuxiaojie
 */
object NetRequest {

    var client = OkHttpClient()

    fun <T> get(url: String, c: Class<T>): T? {
        Log.i("NetRequest", "-->  start net : $url")
        val request = Request.Builder()
            .url(url)
            .build()
        try {
            val response = client.newCall(request).execute()
            val result = response.body?.string()
            Log.i("NetRequest", "<-->  response : $result")
            return Gsons.get().fromJson<T>(result, c)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Log.i("NetRequest", "<-->  response : error")
        return null
    }

}