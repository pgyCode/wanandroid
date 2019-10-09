package com.github.pgycode.wanandroid.home

import com.github.pgycode.wanandroid.common.Router

/**
 * @author: xuxiaojie
 */
object Init {


    @JvmStatic
    fun init() {
        Router.map["/home.page"] = "com.github.pgycode.wanandroid.home.frg.HomeFragment"
    }


}