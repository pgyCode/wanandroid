package com.github.pgycode.wanandroid.common

/**
 * @author: xuxiaojie
 */

object Router {

    val map = HashMap<String, String>()

    fun get(name: String): String {
        map[name]?.let {
            try {
                Class.forName(it)
                return it
            } catch (e: ClassNotFoundException) { }
        }
        return "com.github.pgycode.wanandroid.app.frg.NoneFragment"
    }

}