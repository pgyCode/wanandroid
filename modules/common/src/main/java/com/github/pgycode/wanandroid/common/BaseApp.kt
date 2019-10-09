package com.github.pgycode.wanandroid.common

import android.content.Context

/**
 * @author: xuxiaojie
 */

object BaseApp {

    lateinit var mOnAppCreateListener: OnAppCreateListener

    fun setOnAppCreateListener(onAppCreateListener: OnAppCreateListener) {
        mOnAppCreateListener = onAppCreateListener
    }

    fun get(): Context {
        return mOnAppCreateListener.get()
    }

    interface OnAppCreateListener {
        fun get(): Context
    }

}