package com.github.pgycode.wanandroid.common

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.common.R

/**
 * @author: xuxiaojie
 */
open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar)
    }

    override fun getResources(): Resources {
        AutoSizeUtils.convertToAutoSize(super.getResources())
        return super.getResources()
    }

}