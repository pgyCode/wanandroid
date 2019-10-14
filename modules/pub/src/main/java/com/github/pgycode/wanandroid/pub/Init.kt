package com.github.pgycode.wanandroid.pub

import com.github.pgycode.wanandroid.common.Router

/**
 * @author: xuxiaojie
 */
object Init {


    @JvmStatic
    fun init() {
        Router.map["/pub.page"] = "com.github.pgycode.wanandroid.pub.frg.HomeFragment"
    }


}