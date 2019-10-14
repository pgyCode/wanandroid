package com.github.pgycode.wanandroid.pub.act

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.pgycode.wanandroid.common.BaseActivity
import com.github.pgycode.wanandroid.common.GlideUtil
import com.github.pgycode.wanandroid.pub.R
import com.github.pgycode.wanandroid.pub.frg.HomeFragment
import com.github.pgycode.wanandroid.pub.vm.PrjVM
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.item_prj.*
import kotlinx.android.synthetic.main.item_prj.view.*

/**
 * @author: xuxiaojie
 */
class PrjActivity: BaseActivity() {

    private lateinit var list: RecyclerView
    private lateinit var mAdapter: MyAdapter
    private lateinit var refreshLayout: SmartRefreshLayout

    private lateinit var prjVM: PrjVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_prj)

        prjVM = ViewModelProviders.of(this).get(PrjVM::class.java)
        prjVM.initCid(intent.getIntExtra("cid", 0))

        list = findViewById(R.id.list)
        refreshLayout = findViewById(R.id.refreshLayout)

        refreshLayout.setOnLoadMoreListener {
            prjVM.load()
        }

        prjVM.load()

        mAdapter = MyAdapter()
        list.adapter = mAdapter
        list.layoutManager = LinearLayoutManager(this)

        prjVM.ldRefresh.observe(this, Observer {
            mAdapter.notifyDataSetChanged()
        })
        prjVM.ldLoaderMore.observe(this, Observer {
            refreshLayout.finishLoadMore(it)
        })
    }

    inner class MyAdapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.img?.let {
                GlideUtil.load(this@PrjActivity, it, prjVM.data[position].envelopePic)
                it.setOnClickListener {
                    val intent = Intent(this@PrjActivity, WebviewActivity::class.java)
                    intent.putExtra("url", prjVM.data[position].link)
                    intent.putExtra("title", prjVM.data[position].desc)
                    startActivity(intent)
                }
            }
            holder.txtAuthor?.let {
                it.text = prjVM.data[position].chapterName
            }
            holder.txtTitle?.let {
                it.text = prjVM.data[position].desc
            }
        }

        override fun getItemCount(): Int {
            return prjVM.data.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(View.inflate(this@PrjActivity, R.layout.item_prj, null))
        }

        inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            var txtAuthor: TextView? = null
            var img: ImageView? = null
            var txtTitle: TextView? = null
            var card: CardView? = null
            init {
                txtAuthor = itemView.findViewById(R.id.txt_author)
                img = itemView.findViewById(R.id.img)
                txtTitle = itemView.findViewById(R.id.txt_title)
                card = itemView.findViewById(R.id.card)
                card?.let {
                    it.radius = 10f
                    it.cardElevation = 10f
                    it.setCardBackgroundColor(0xe5e5e5)
                    it.requestApplyInsets()
                }
            }
        }

    }
}