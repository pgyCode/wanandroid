package com.github.pgycode.wanandroid.blog.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.pgycode.wanandroid.blog.bean.BlogBean
import com.github.pgycode.wanandroid.common.NetRequest
import com.github.pgycode.wanandroid.common.ThreadPool
import com.github.pgycode.wanandroid.blog.bean.NameBean
import com.github.pgycode.wanandroid.blog.bean.PrjBean
import com.github.pgycode.wanandroid.blog.bean.PrjBotBean

/**
 * @author: xuxiaojie
 */
class HomeVM: ViewModel() {

    val ldRefresh: MutableLiveData<Void> = MutableLiveData()

    val data: ArrayList<BlogBean.DataBean> = ArrayList()
    val dataBot: ArrayList<PrjBotBean.DataBean.DatasBean> = ArrayList()

    var page = 0

    var cId = 0

    fun init() {
        ThreadPool.network.execute {
            // 请求网络
            val blogBean = NetRequest.get("https://www.wanandroid.com/tree/json",
                BlogBean::class.java)

            var success = false
            blogBean?.data?.let {
                data.addAll(it)
                success = true
                page++
            }

            if (success) {
                ldRefresh.postValue(null)
            }
        }
    }

    fun load() {
        ThreadPool.network.execute {
            // 请求网络
            val prjBotBean = NetRequest.get("https://www.wanandroid.com/article/list/$page/json?cid=$cId",
                PrjBotBean::class.java)

            var success = false
            prjBotBean?.data?.datas?.let {
                dataBot.addAll(it)
                success = true
                page++
            }

            if (success) {
                ldRefresh.postValue(null)
            }
        }
    }
}