package com.github.pgycode.wanandroid.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * @author: xuxiaojie
 */
object ThreadPool {

    @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
    val network = ThreadPoolExecutor(0, 5, 60, TimeUnit.SECONDS, LinkedBlockingDeque<Runnable>())

}