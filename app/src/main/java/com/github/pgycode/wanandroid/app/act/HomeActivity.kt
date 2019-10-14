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
    lateinit var btnPub: View
    lateinit var btnNav: View

    lateinit var txtPrj: TextView
    lateinit var txtBlog: TextView
    lateinit var txtPub: TextView
    lateinit var txtNav: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        btnPrj = findViewById(R.id.btn_prj)
        btnPub = findViewById(R.id.btn_pub)
        btnNav = findViewById(R.id.btn_nav)

        txtPrj = findViewById(R.id.txt_prj)
        txtBlog = findViewById(R.id.txt_blog)
        txtPub = findViewById(R.id.txt_pub)
        txtNav = findViewById(R.id.txt_nav)

        btnPrj.setOnClickListener {
            txtPrj.setTextColor(0xff2196F3.toInt())
            txtPrj.setTypeface(null, Typeface.BOLD)
            txtBlog.setTextColor(0xff000000.toInt())
            txtBlog.setTypeface(null, Typeface.NORMAL)
            txtPub.setTextColor(0xff000000.toInt())
            txtPub.setTypeface(null, Typeface.NORMAL)
            txtNav.setTextColor(0xff000000.toInt())
            txtNav.setTypeface(null, Typeface.NORMAL)
            openPrj()
        }

        btnBlog = findViewById(R.id.btn_blog)
        btnBlog.setOnClickListener {
            txtBlog.setTextColor(0xff2196F3.toInt())
            txtBlog.setTypeface(null, Typeface.BOLD)
            txtPrj.setTextColor(0xff000000.toInt())
            txtPrj.setTypeface(null, Typeface.NORMAL)
            txtPub.setTextColor(0xff000000.toInt())
            txtPub.setTypeface(null, Typeface.NORMAL)
            txtNav.setTextColor(0xff000000.toInt())
            txtNav.setTypeface(null, Typeface.NORMAL)
            openBlog()
        }

        btnPub.setOnClickListener {
            txtPub.setTextColor(0xff2196F3.toInt())
            txtPub.setTypeface(null, Typeface.BOLD)
            txtPrj.setTextColor(0xff000000.toInt())
            txtPrj.setTypeface(null, Typeface.NORMAL)
            txtBlog.setTextColor(0xff000000.toInt())
            txtBlog.setTypeface(null, Typeface.NORMAL)
            txtNav.setTextColor(0xff000000.toInt())
            txtNav.setTypeface(null, Typeface.NORMAL)
            openPub()
        }

        btnNav.setOnClickListener {
            txtNav.setTextColor(0xff2196F3.toInt())
            txtNav.setTypeface(null, Typeface.BOLD)
            txtPrj.setTextColor(0xff000000.toInt())
            txtPrj.setTypeface(null, Typeface.NORMAL)
            txtBlog.setTextColor(0xff000000.toInt())
            txtBlog.setTypeface(null, Typeface.NORMAL)
            txtPub.setTextColor(0xff000000.toInt())
            txtPub.setTypeface(null, Typeface.NORMAL)
            openNav()
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

    fun openPub() {
        try {
            PluginManager.getInstance(BaseApp.get()).loadPlugin(Plugin.get("pub"), "com.github.pgycode.wanandroid.pub.Init")
        } catch (ignore: Exception) {
            ignore.printStackTrace()
        }
        supportFragmentManager.beginTransaction().replace(R.id.container, Class.forName(Router.get("/pub.page")).newInstance() as Fragment).commit()
    }

    fun openNav() {
        try {
            PluginManager.getInstance(BaseApp.get()).loadPlugin(Plugin.get("nav"), "com.github.pgycode.wanandroid.nav.Init")
        } catch (ignore: Exception) {
            ignore.printStackTrace()
        }
        supportFragmentManager.beginTransaction().replace(R.id.container, Class.forName(Router.get("/nav.page")).newInstance() as Fragment).commit()
    }

}