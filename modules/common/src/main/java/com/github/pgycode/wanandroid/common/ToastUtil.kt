package com.github.pgycode.wanandroid.common

import android.widget.Toast

/**
 * @author: xuxiaojie
 */
object ToastUtil {

    val toast = Toast.makeText(BaseApp.get(), "", Toast.LENGTH_SHORT);

    fun show(string: String) {
        toast.setText(string)
        toast.show()
    }

}