package com.github.pgycode.wanandroid.common

import android.app.Activity
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.common.R

/**
 * @author: xuxiaojie
 */

object GlideUtil {

    fun load(fragment: Fragment, imageView: ImageView, url: String) {
        Glide.with(fragment)
            .load(url)
            .placeholder(R.mipmap.timg)
            .into(imageView)
    }


    fun load(activity: Activity, imageView: ImageView, url: String) {
        Glide.with(activity)
            .load(url)
            .placeholder(R.mipmap.timg)
            .into(imageView)
    }

}