package com.github.pgycode.wanandroid.app.app

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.didi.virtualapk.PluginManager
import com.github.pgycode.wanandroid.common.BaseApp
import kotlin.concurrent.thread

/**
 * @author: xuxiaojie
 */

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        BaseApp.setOnAppCreateListener(object: BaseApp.OnAppCreateListener {
            override fun get(): Context {
                return this@App
            }
        })
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        PluginManager.getInstance(base).init()
    }

}