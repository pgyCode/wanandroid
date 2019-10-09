package com.github.pgycode.wanandroid.app.act

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.didi.virtualapk.PluginManager
import com.github.pgycode.wanandroid.app.R
import com.github.pgycode.wanandroid.app.router.Plugin
import com.github.pgycode.wanandroid.common.Router
import com.github.pgycode.wanandroid.common.BaseActivity
import com.github.pgycode.wanandroid.common.BaseApp

/**
 * @author: xuxiaojie
 */

class HomeActivity: BaseActivity() {

    lateinit var btnPrj: View
    lateinit var btnBlog: View

    lateinit var txtPrj: TextView
    lateinit var txtBlog: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        btnPrj = findViewById(R.id.btn_prj)
        txtPrj = findViewById(R.id.txt_prj)
        txtBlog = findViewById(R.id.txt_blog)
        btnPrj.setOnClickListener {
            txtPrj.setTextColor(0xff2196F3.toInt())
            txtPrj.setTypeface(null, Typeface.BOLD)
            txtBlog.setTextColor(0xff000000.toInt())
            txtBlog.setTypeface(null, Typeface.NORMAL)
            openPrj()
        }

        btnBlog = findViewById(R.id.btn_blog)
        btnBlog.setOnClickListener {
            txtBlog.setTextColor(0xff2196F3.toInt())
            txtBlog.setTypeface(null, Typeface.BOLD)
            txtPrj.setTextColor(0xff000000.toInt())
            txtPrj.setTypeface(null, Typeface.NORMAL)
            openBlog()
        }

        // 初始打开home
        openPrj()
    }

    fun openPrj() {
        try {
            PluginManager.getInstance(BaseApp.get()).loadPlugin(Plugin.get("home"), "com.github.pgycode.wanandroid.home.Init")
        } catch (ignore: Exception) {
            ignore.printStackTrace()
        }
        supportFragmentManager.beginTransaction().replace(R.id.container, Class.forName(Router.get("/home.page")).newInstance() as Fragment).commit()
    }

    fun openBlog() {
        try {
            PluginManager.getInstance(BaseApp.get()).loadPlugin(Plugin.get("blog"), "com.github.pgycode.wanandroid.blog.Init")
        } catch (ignore: Exception) {
            ignore.printStackTrace()
        }
        supportFragmentManager.beginTransaction().replace(R.id.container, Class.forName(Router.get("/blog.page")).newInstance() as Fragment).commit()
    }

}