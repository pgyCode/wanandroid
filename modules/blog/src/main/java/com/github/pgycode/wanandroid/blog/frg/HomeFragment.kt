package com.github.pgycode.wanandroid.home.frg

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.pgycode.wanandroid.common.BaseFragment
import com.github.pgycode.wanandroid.common.GlideUtil
import com.github.pgycode.wanandroid.common.ToastUtil
import com.github.pgycode.wanandroid.home.R
import com.github.pgycode.wanandroid.home.act.PrjActivity
import com.github.pgycode.wanandroid.home.act.WebviewActivity
import com.github.pgycode.wanandroid.home.vm.HomeVM
import com.scwang.smartrefresh.layout.SmartRefreshLayout


/**
 * @author: xuxiaojie
 */

class HomeFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.frg_home, null)


    private lateinit var list: RecyclerView
    private lateinit var mAdapter: MyAdapter
    private lateinit var viewFailed: View
    private lateinit var viewLoading: View
    private lateinit var refreshLayout: SmartRefreshLayout

    private lateinit var homeVM: HomeVM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeVM = ViewModelProviders.of(this).get(HomeVM::class.java)

        list = view.findViewById(R.id.list)
        viewFailed = view.findViewById(R.id.view_failed)
        viewLoading = view.findViewById(R.id.view_loading)

        refreshLayout = view.findViewById(R.id.refreshLayout)
        refreshLayout.setOnRefreshListener { refreshlayout ->
            refreshlayout.finishRefresh(2000/*,false*/)//传入false表示刷新失败
        }
        refreshLayout.setOnLoadMoreListener { refreshlayout ->
            load()
        }

        initList()

        initObserver()

        init()
    }

    fun initObserver() {
        homeVM.ldState.observe(this, Observer{
            when(it) {
                0 -> {
                    viewLoading.visibility = View.VISIBLE
                    viewFailed.visibility = View.GONE
                    list.visibility = View.GONE
                }
                -1 -> {
                    list.visibility = View.GONE
                    viewLoading.visibility = View.GONE
                    viewFailed.visibility = View.VISIBLE
                }
                1 -> {
                    list.visibility = View.VISIBLE
                    viewFailed.visibility = View.GONE
                    viewLoading.visibility = View.GONE
                }
            }
        })

        homeVM.ldRefresh.observe(this, Observer {
            mAdapter.notifyDataSetChanged()
        })

        homeVM.ldLoaderMore.observe(this, Observer {
            refreshLayout.finishLoadMore(it)
            ToastUtil.show(if (it) {"加载成功"} else {"加载失败"} )
        })
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

    fun init() {
        homeVM.init()
    }

    fun load() {
        homeVM.load()
    }

    inner class MyAdapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            if (position < homeVM.prj.size) {
                holder.txtTitle?.let {
                    if (position < homeVM.prj.size) {
                        it.text = homeVM.prj[position].name
                    } else {
                        it.text = ""
                    }
                }
                holder.itemView.setOnClickListener {
                    val intent = Intent(activity, PrjActivity::class.java)
                    intent.putExtra("cid", homeVM.prj[position].id)
                    startActivity(intent)
                }
            } else {
                val poi = position - homeVM.prj.size
                holder.img?.let {
                    GlideUtil.load(this@HomeFragment, it, homeVM.data[poi].envelopePic)
                    it.setOnClickListener {
                        val intent = Intent(activity, WebviewActivity::class.java)
                        intent.putExtra("url", homeVM.data[poi].link)
                        intent.putExtra("title", homeVM.data[poi].desc)
                        startActivity(intent)
                    }
                }
                holder.txtAuthor?.let {
                    it.text = homeVM.data[poi].desc
                }
                holder.txtTitle?.let {
                    it.text = homeVM.data[poi].chapterName
                }
            }
        }

        override fun getItemCount(): Int {
            return homeVM.data.size + homeVM.prj.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return when (viewType) {
                1 -> MyViewHolder(View.inflate(requireContext(), R.layout.item_home_name, null))
                2 -> MyViewHolder(View.inflate(requireContext(), R.layout.item_home, null))
                else -> MyViewHolder(View.inflate(requireContext(), R.layout.item_home, null))
            }
        }

        inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            var txtAuthor: TextView? = null
            var img: ImageView? = null
            var txtTitle: TextView? = null
            init {
                txtAuthor = itemView.findViewById(R.id.txt_author)
                img = itemView.findViewById(R.id.img)
                txtTitle = itemView.findViewById(R.id.txt_title)
            }
        }

        override fun getItemViewType(position: Int): Int {
            return if (position < homeVM.prj.size) {
                1
            } else {
                2
            }
        }
    }
}