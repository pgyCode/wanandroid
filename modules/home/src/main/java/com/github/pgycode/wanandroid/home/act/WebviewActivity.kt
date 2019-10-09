package com.github.pgycode.wanandroid.home.act

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.github.pgycode.wanandroid.common.BaseActivity
import com.github.pgycode.wanandroid.home.R
import kotlinx.android.synthetic.main.act_web.*

/**
 * @author: xuxiaojie
 */
class WebviewActivity: BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_web)

        val mWebView = findViewById<WebView>(R.id.web)
        mWebView.settings.javaScriptEnabled = true
        mWebView.webViewClient = WebViewClient()
        mWebView.loadUrl(intent.getStringExtra("url"))
        mWebView.webChromeClient = object: WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                pro.text = "加载 $newProgress%"
            }
        }

        findViewById<TextView>(R.id.txt).text = intent.getStringExtra("title")
    }

}