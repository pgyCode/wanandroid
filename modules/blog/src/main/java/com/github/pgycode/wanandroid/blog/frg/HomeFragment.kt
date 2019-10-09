package com.github.pgycode.wanandroid.blog.frg

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.pgycode.wanandroid.common.BaseFragment
import com.github.pgycode.wanandroid.blog.R
import com.github.pgycode.wanandroid.blog.act.WebviewActivity
import com.github.pgycode.wanandroid.blog.vm.HomeVM


/**
 * @author: xuxiaojie
 */

class HomeFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.frg_blog, null)

    private lateinit var listOne: RecyclerView
    private lateinit var listTwo: RecyclerView
    private lateinit var listThree: RecyclerView
    private lateinit var mAdapter: MyAdapter
    private lateinit var mAdapterTwo: MyAdapterTwo
    private lateinit var mAdapterThree: MyAdapterThree

    private lateinit var homeVM: HomeVM

    private var poi = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeVM = ViewModelProviders.of(this).get(HomeVM::class.java)

        listOne = view.findViewById(R.id.list_one)
        listTwo = view.findViewById(R.id.list_two)
        listThree = view.findViewById(R.id.list_three)

        initList()

        initObserver()

        init()
    }

    fun initObserver() {

        homeVM.ldRefresh.observe(this, Observer {
            mAdapter.notifyDataSetChanged()
            mAdapterTwo.notifyDataSetChanged()
            mAdapterThree.notifyDataSetChanged()
        })

    }

    fun initList() {
        mAdapter = MyAdapter()
        listOne.adapter = mAdapter
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        listOne.layoutManager = linearLayoutManager

        mAdapterTwo = MyAdapterTwo()
        listTwo.adapter = mAdapterTwo
        val linearLayoutManagerTwo = LinearLayoutManager(requireContext())
        linearLayoutManagerTwo.orientation = LinearLayoutManager.HORIZONTAL
        listTwo.layoutManager = linearLayoutManagerTwo

        mAdapterThree = MyAdapterThree()
        listThree.adapter = mAdapterThree
        listThree.layoutManager = LinearLayoutManager(requireContext())
    }

    fun init() {
        homeVM.init()
    }

    inner class MyAdapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.txt?.let {
                it.text = homeVM.data[position].name
                it.setOnClickListener {
                    poi = position
                    mAdapterTwo.notifyDataSetChanged()
                }
            }
        }

        override fun getItemCount(): Int {
            return homeVM.data.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(View.inflate(requireContext(), R.layout.item_home, null))
        }

        inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            var txt: TextView? = null
            init {
                txt = itemView.findViewById(R.id.txt)
            }
        }
    }

    inner class MyAdapterTwo: RecyclerView.Adapter<MyAdapterTwo.MyViewHolder>() {

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.txt?.let {
                it.text = homeVM.data[poi].children[position].name
            }
            holder.itemView.setOnClickListener {
                homeVM.cId = homeVM.data[poi].children[position].id
                homeVM.dataBot.clear()
                homeVM.page = 0
                homeVM.load()
            }
        }

        override fun getItemCount(): Int {
            return if (poi == -1) {
                0
            } else {
                homeVM.data[poi].children.size
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(View.inflate(requireContext(), R.layout.item_home, null))
        }

        inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            var txt: TextView? = null
            init {
                txt = itemView.findViewById(R.id.txt)
            }
        }
    }

    inner class MyAdapterThree: RecyclerView.Adapter<MyAdapterThree.MyViewHolder>() {
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.txt?.let {
                it.text = homeVM.dataBot[position].title
            }
            holder.itemView.setOnClickListener {
                val intent = Intent(activity, WebviewActivity::class.java)
                intent.putExtra("url", homeVM.dataBot[position].link)
                intent.putExtra("title", homeVM.dataBot[position].title)
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return homeVM.dataBot.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(View.inflate(requireContext(), R.layout.item_home_bot, null))
        }

        inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            var txt: TextView? = null
            init {
                txt = itemView.findViewById(R.id.txt)
            }
        }
    }
}