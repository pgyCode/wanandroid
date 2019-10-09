package com.github.pgycode.wanandroid.home.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.pgycode.wanandroid.common.NetRequest
import com.github.pgycode.wanandroid.common.ThreadPool
import com.github.pgycode.wanandroid.home.bean.NameBean
import com.github.pgycode.wanandroid.home.bean.PrjBean

/**
 * @author: xuxiaojie
 */
class HomeVM: ViewModel() {

    val ldRefresh: MutableLiveData<Void> = MutableLiveData()

    val ldLoaderMore: MutableLiveData<Boolean> = MutableLiveData()

    val ldState: MutableLiveData<Int> = MutableLiveData()

    val data: ArrayList<PrjBean.DataBean.DatasBean> = ArrayList()

    val prj: ArrayList<NameBean.DataBean> = ArrayList()

    var page = 0;

    fun init() {
        ThreadPool.network.execute {
            // 加载中
            ldState.postValue(0)
            // 请求网络
            val nameBean = NetRequest.get("https://www.wanandroid.com/project/tree/json",
                NameBean::class.java)
            val prjBean = NetRequest.get(
                "https://wanandroid.com/article/listproject/0/json",
                PrjBean::class.java)

            var success = false
            prjBean?.data?.datas?.let {
                data.addAll(it)
                success = true
                page++
            }

            nameBean?.data?.let {
                prj.addAll(it)
                success = true
            }

            if (success) {
                ldState.postValue(1)
                ldRefresh.postValue(null)
            } else {
                ldState.postValue(-1)
            }
        }
    }

    fun load() {
        ThreadPool.network.execute {
            // 请求网络
            val prjBean = NetRequest.get(
                "https://wanandroid.com/article/listproject/$page/json",
                PrjBean::class.java)

            prjBean?.data?.datas?.let {
                data.addAll(it)
                ldRefresh.postValue(null)
                ldLoaderMore.postValue(true)
                page++
                return@execute
            }
            ldLoaderMore.postValue(false)
        }
    }

}