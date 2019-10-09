package com.github.pgycode.wanandroid.nav.vm

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.pgycode.wanandroid.nav.bean.BlogBean
import com.github.pgycode.wanandroid.common.NetRequest
import com.github.pgycode.wanandroid.common.ThreadPool
import com.github.pgycode.wanandroid.nav.bean.NameBean
import com.github.pgycode.wanandroid.nav.bean.PrjBean
import com.github.pgycode.wanandroid.nav.bean.PrjBotBean
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

    val data: ArrayList<BlogBean.DataBean.ArticlesBean> = ArrayList()

    val prj: CopyOnWriteArrayList<BlogBean.DataBean> = CopyOnWriteArrayList()

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
            val blogBean = NetRequest.get(
                "https://www.wanandroid.com/navi/json",
                BlogBean::class.java)

            if (blogBean?.data != null) {
                prj.addAll(blogBean.data)
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
     * 行为：切换标签
     */
    fun checkTab(poi: Int) {
        ThreadPool.network.execute {
            data.clear()
            data.addAll(prj[poi].articles)
            ldListRefresh.postValue(null)
        }
    }
}