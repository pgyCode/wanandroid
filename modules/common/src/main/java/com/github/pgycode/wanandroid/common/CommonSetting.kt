package com.github.pgycode.wanandroid.common

import com.tencent.mmkv.MMKV

/**
 * @author: xuxiaojie
 */
object CommonSetting {

    const val TOKEN = "TOKEN"
    const val REFRESH_TOKEN = "REFRESH_TOKEN"

    private val mmkv: MMKV
    init {
        MMKV.initialize(BaseApp.get())
        mmkv = MMKV.defaultMMKV()
    }

    fun putString(key: String, string: String?) {
        string?.let {
            mmkv.encode(key, it)
        }
    }

    fun getString(key: String, default: String): String {
        return mmkv.decodeString(key, default)
    }

}