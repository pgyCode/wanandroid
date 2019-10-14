package com.github.pgycode.wanandroid.blog.frg

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidkun.xtablayout.XTabLayout
import com.github.pgycode.wanandroid.common.BaseFragment
import com.github.pgycode.wanandroid.blog.R
import com.github.pgycode.wanandroid.blog.act.WebviewActivity
import com.github.pgycode.wanandroid.blog.vm.HomeVM
import com.github.pgycode.wanandroid.common.GlideUtil
import com.scwang.smartrefresh.layout.SmartRefreshLayout


/**
 * @author: xuxiaojie
 */

class HomeFragment: BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.frg_blog, null)

    private lateinit var list: RecyclerView
    private lateinit var mAdapter: MyAdapter
    private lateinit var innerFailed: View
    private lateinit var innerLoading: View
    private lateinit var outerFailed: View
    private lateinit var outerLoading: View
    private lateinit var outerContainer: View
    private lateinit var refreshLayout: SmartRefreshLayout

    private lateinit var tab: XTabLayout
    private lateinit var tabSecond: XTabLayout

    private lateinit var homeVM: HomeVM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeVM = ViewModelProviders.of(this).get(HomeVM::class.java)

        list = view.findViewById(R.id.list)
        innerFailed = view.findViewById(R.id.inner_failed)
        innerLoading = view.findViewById(R.id.inner_loading)
        outerFailed = view.findViewById(R.id.outer_failed)
        outerLoading = view.findViewById(R.id.outer_loading)
        outerContainer = view.findViewById(R.id.outer_container);
        tab = view.findViewById(R.id.tab)
        tab.tabMode = XTabLayout.MODE_SCROLLABLE
        tab.setTabTextColors(0xffe5e5e5.toInt(), 0xffffffff.toInt())
        tab.setDividerSize(50,20)
        tab.setDividerColor(0x00000000)
        tab.setOnTabSelectedListener(object: XTabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: XTabLayout.Tab?) {
                tab?.let {
                    homeVM.checkFirstTab(tab.position)
                    tabSecond.removeAllTabs()
                    tab.position.let {
                        for (prj in homeVM.prj[it].children) {
                            tabSecond.addTab(tabSecond.newTab().setText(prj.name))
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: XTabLayout.Tab?) {}

            override fun onTabReselected(tab: XTabLayout.Tab?) {}
        })
        tabSecond = view.findViewById(R.id.tab_second)
        tabSecond.tabMode = XTabLayout.MODE_SCROLLABLE
        tabSecond.setTabTextColors(0xffe5e5e5.toInt(), 0xffffffff.toInt())
        tabSecond.setDividerSize(50,20)
        tabSecond.setDividerColor(0x00000000)
        tabSecond.setOnTabSelectedListener(object: XTabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: XTabLayout.Tab?) {
                tab?.let {
                    homeVM.checkTab(tab.position);
                }
            }

            override fun onTabUnselected(tab: XTabLayout.Tab?) {
            }

            override fun onTabReselected(tab: XTabLayout.Tab?) {
            }
        })
        refreshLayout = view.findViewById(R.id.refreshLayout)
        refreshLayout.setOnRefreshListener { homeVM.downRefresh() }
        refreshLayout.setOnLoadMoreListener { homeVM.load() }

        initList()

        initObserver()

        homeVM.init()
    }

    fun initObserver() {
        homeVM.ldOuterLoadingVisiable.observe(this, Observer { outerLoading.visibility = it })
        homeVM.ldOuterFailedVisiable.observe(this, Observer { outerFailed.visibility = it })
        homeVM.ldOutContainerVisiable.observe(this, Observer { outerContainer.visibility = it })
        homeVM.ldInnerFailedVisiable.observe(this, Observer { innerFailed.visibility = it })
        homeVM.ldInnerLoadingVisiable.observe(this, Observer { innerLoading.visibility = it })
        homeVM.ldRefreshLayoutVisiable.observe(this, Observer { refreshLayout.visibility = it })

        homeVM.ldTabRefresh.observe(this, Observer {
            for (prj in homeVM.prj) {
                tab.addTab(tab.newTab().setText(prj.name))
            }
        })

        homeVM.ldListRefresh.observe(this, Observer {
            mAdapter.notifyDataSetChanged()
        })

        homeVM.ldRefreshLayoutStopPullUpLoad.observe(this, Observer { refreshLayout.finishLoadMore() })
        homeVM.ldRefreshLayoutStopPullDownRefresh.observe(this, Observer { refreshLayout.finishRefresh() })
    }

    fun initList() {
        mAdapter = MyAdapter()
        list.adapter = mAdapter
        val manager = GridLayoutManager(activity,4)
        manager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val type = list.adapter?.getItemViewType(position);
                return if (type == 1) { 1 } else { 4 }
            }
        }
        list.layoutManager = manager
    }

    inner class MyAdapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.img?.let {
                GlideUtil.load(this@HomeFragment, it, homeVM.data[position].envelopePic)
            }
            holder.txtAuthor?.let {
                it.text = homeVM.data[position].author
            }
            holder.txtTitle?.let {
                it.text = homeVM.data[position].title
            }
            holder.txtTime?.let {
                it.text = homeVM.data[position].niceDate
            }
            holder.txtDesc?.let {
                it.text = homeVM.data[position].desc
            }
            holder.itemView.setOnClickListener {
                val intent = Intent(activity, WebviewActivity::class.java)
                intent.putExtra("url", homeVM.data[position].link)
                intent.putExtra("title", homeVM.data[position].desc)
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return homeVM.data.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(View.inflate(requireContext(), R.layout.item_home, null))
        }

        inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            var txtAuthor: TextView? = null
            var txtTime: TextView? = null
            var img: ImageView? = null
            var txtTitle: TextView? = null
            var txtDesc: TextView? = null
            init {
                txtAuthor = itemView.findViewById(R.id.txt_author)
                img = itemView.findViewById(R.id.img)
                txtTime = itemView.findViewById(R.id.txt_time)
                txtTitle = itemView.findViewById(R.id.txt_title)
            }
        }
    }
}