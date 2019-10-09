package com.github.pgycode.wanandroid.nav.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.pgycode.wanandroid.common.NetRequest
import com.github.pgycode.wanandroid.common.ThreadPool
import com.github.pgycode.wanandroid.nav.bean.NameBean
import com.github.pgycode.wanandroid.nav.bean.PrjBean
import com.github.pgycode.wanandroid.nav.bean.PrjTopBean

/**
 * @author: xuxiaojie
 */
class PrjVM: ViewModel() {

    val ldRefresh: MutableLiveData<Void> = MutableLiveData()

    val ldLoaderMore: MutableLiveData<Boolean> = MutableLiveData()

    val data: ArrayList<PrjTopBean.DataBean.DatasBean> = ArrayList()

    var page = 1

    var mCid = 293

    fun initCid(cid: Int) {
        mCid = cid
    }

    fun load() {
        ThreadPool.network.execute {
            // 请求网络
            val prjBean = NetRequest.get(
                "https://www.wanandroid.com/project/list/$page/json?cid=$mCid",
                PrjTopBean::class.java)

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