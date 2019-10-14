package com.github.pgycode.wanandroid.pub.vm

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.pgycode.wanandroid.common.NetRequest
import com.github.pgycode.wanandroid.common.ThreadPool
import com.github.pgycode.wanandroid.pub.bean.*
import java.util.concurrent.CopyOnWriteArrayList

/**
 * @author: xuxiaojie
 */
class HomeVM: ViewModel() {

    val ldOuterLoadingVisiable: MutableLiveData<Int> = MutableLiveData()
    val ldOuterFailedVisiable: MutableLiveData<Int> = MutableLiveData()
    val ldInnerLoadingVisiable: MutableLiveData<Int> = MutableLiveData()
    val ldRefreshLayoutVisiable: MutableLiveData<Int> = MutableLiveData()
    val ldInnerFailedVisiable: MutableLiveData<Int> = MutableLiveData()
    val ldOutContainerVisiable: MutableLiveData<Int> = MutableLiveData()

    val ldTabRefresh: MutableLiveData<Void> = MutableLiveData()
    val ldListRefresh: MutableLiveData<Void> = MutableLiveData()

    val ldRefreshLayoutStopPullUpLoad: MutableLiveData<Void> = MutableLiveData()
    val ldRefreshLayoutStopPullDownRefresh: MutableLiveData<Void> = MutableLiveData()

    val data: ArrayList<PrjBotBean.DataBean.DatasBean> = ArrayList()

    val prj: CopyOnWriteArrayList<PubBean.DataBean> = CopyOnWriteArrayList()

    var page = 0

    var firstTab = 0

    var cid = 0

    /**
     * 行为：初始化
     */
    fun init() {
        ThreadPool.network.execute {
            // 加载中
            ldOutContainerVisiable.postValue(View.GONE)
            ldOuterFailedVisiable.postValue(View.GONE)
            ldOuterLoadingVisiable.postValue(View.VISIBLE)

            // 加载顶部数据
            val pubBean = NetRequest.get(
                "https://wanandroid.com/wxarticle/chapters/json",
                PubBean::class.java)

            if (pubBean?.data != null) {
                prj.addAll(pubBean.data)
            } else {
                ldOuterFailedVisiable.postValue(View.VISIBLE)
                ldOuterLoadingVisiable.postValue(View.GONE)
            }

            ldTabRefresh.postValue(null)
            ldListRefresh.postValue(null)
            ldOuterLoadingVisiable.postValue(View.GONE)
            ldOutContainerVisiable.postValue(View.VISIBLE)
        }
    }

    /**
     * 行为：上拉加载
     */
    fun load() {
        ThreadPool.network.execute {
            // 请求网络
            val prjBean = NetRequest.get(
                "https://www.wanandroid.com/article/list/$page/json?cid=$cid",
                PrjBotBean::class.java)

            if (prjBean?.data?.datas != null) {
                data.addAll(prjBean.data.datas)
                ldListRefresh.postValue(null)
                page++
            }
            ldRefreshLayoutStopPullUpLoad.postValue(null)
        }
    }

    /**
     * 行为：下拉刷新
     */
    fun downRefresh() {
        ThreadPool.network.execute {
            page = 0
            // 请求网络
            val prjBean = NetRequest.get(
                "https://www.wanandroid.com/article/list/$page/json?cid=$cid",
                PrjBotBean::class.java)
            if (prjBean?.data?.datas != null) {
                data.clear()
                data.addAll(prjBean.data.datas)
                ldListRefresh.postValue(null)
                page++
            }
            ldRefreshLayoutStopPullDownRefresh.postValue(null)
        }
    }

    /**
     * 行为：切换一级标签
     */
    fun checkFirstTab(poi: Int) {
        firstTab = poi
    }

    /**
     * 行为：切换标签
     */
    fun checkTab(poi: Int) {
        ThreadPool.network.execute {
            data.clear()
            ldListRefresh.postValue(null)
            cid = prj[poi].id
            page = 0

            ldInnerFailedVisiable.postValue(View.GONE)
            ldInnerLoadingVisiable.postValue(View.VISIBLE)
            ldRefreshLayoutVisiable.postValue(View.GONE)

            // 请求网络
            val prjBean = NetRequest.get(
                "https://www.wanandroid.com/wxarticle/list/$cid/$page/json",
                PrjBotBean::class.java)
            if (prjBean?.data?.datas != null) {
                data.clear()
                data.addAll(prjBean.data.datas)
                ldListRefresh.postValue(null)
                ldRefreshLayoutVisiable.postValue(View.VISIBLE)
                page++
            } else {
                ldInnerFailedVisiable.postValue(View.VISIBLE)
            }
            ldInnerLoadingVisiable.postValue(View.GONE)
        }
    }
}