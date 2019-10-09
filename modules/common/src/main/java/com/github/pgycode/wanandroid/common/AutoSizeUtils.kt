package com.github.pgycode.wanandroid.common

import android.content.res.Resources
import kotlin.math.min

/**
 *作者：胥晓杰
 *邮箱：xuxiaojie@youzan.com
 */
object AutoSizeUtils {

    // 标记默认Density值，防止不断赋值，造成计算量过大
    private const val DensityDefault = -1F

    // 标记设计尺寸
    private const val DesignWidth = 375F


    // 主屏Density值，只有为默认值才会被赋值
    private var densityMain = DensityDefault

    /**
     * 功能：默认转换方法，用于默认转换主屏的内容
     */
    @JvmStatic
    fun convertToAutoSize(resources: Resources) {
        // 即将被改变的displayMetrics
        val contextDisplayMetrics = resources.displayMetrics
        if (densityMain == DensityDefault) {
            // 如果为默认值，进行计算
            val screenWidth = min(contextDisplayMetrics.widthPixels, contextDisplayMetrics.heightPixels)
            densityMain = screenWidth / DesignWidth
        }
        contextDisplayMetrics.density =
            densityMain
        contextDisplayMetrics.scaledDensity = contextDisplayMetrics.density
        BaseApp.get().resources.displayMetrics.density = contextDisplayMetrics.density
        BaseApp.get().resources.displayMetrics.scaledDensity = contextDisplayMetrics.density
    }
}
