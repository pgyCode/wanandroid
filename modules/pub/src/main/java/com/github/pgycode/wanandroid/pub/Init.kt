package com.github.pgycode.wanandroid.blog

import com.github.pgycode.wanandroid.common.Router

/**
 * @author: xuxiaojie
 */
object Init {


    @JvmStatic
    fun init() {
        Router.map["/blog.page"] = "com.github.pgycode.wanandroid.blog.frg.HomeFragment"
    }


}