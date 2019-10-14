package com.github.pgycode.wanandroid.app.router

import com.github.pgycode.wanandroid.common.BaseApp
import java.io.File

/**
 * @author: xuxiaojie
 */

object Plugin {

    private val map = HashMap<String, File>()

    init {
        map["home"] = File(File(BaseApp.get().getExternalFilesDir(null), "home.apk").absolutePath.replace("/storage/emulated/10", "/storage/emulated/0"))
        map["blog"] = File(File(BaseApp.get().getExternalFilesDir(null), "blog.apk").absolutePath.replace("/storage/emulated/10", "/storage/emulated/0"))
        map["pub"] = File(File(BaseApp.get().getExternalFilesDir(null), "pub.apk").absolutePath.replace("/storage/emulated/10", "/storage/emulated/0"))
        map["nav"] = File(File(BaseApp.get().getExternalFilesDir(null), "nav.apk").absolutePath.replace("/storage/emulated/10", "/storage/emulated/0"))
    }

    fun get(name: String): File? {
        return map[name]
    }

}