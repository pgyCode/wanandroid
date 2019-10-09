package com.github.pgycode.wanandroid.nav

import com.github.pgycode.wanandroid.common.Router

/**
 * @author: xuxiaojie
 */
object Init {


    @JvmStatic
    fun init() {
        Router.map["/nav.page"] = "com.github.pgycode.wanandroid.nav.frg.HomeFragment"
    }


}