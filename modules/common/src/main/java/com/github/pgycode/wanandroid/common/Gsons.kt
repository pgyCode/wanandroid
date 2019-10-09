package com.github.pgycode.wanandroid.common

import com.google.gson.Gson

/**
 * @author: xuxiaojie
 */
object Gsons {

    val gson = Gson()

    fun get(): Gson {
        return gson
    }
}