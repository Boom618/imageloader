package com.boomhe.imageloader.testclass

import android.content.Context

import com.boomhe.imageloader.ImageLoaderOptions

/**
 * Created by boomhe on 2017/7/13.
 */

interface demo {
    fun showImage(options: ImageLoaderOptions)
    fun cleanMemory(context: Context)
    // 在application的oncreate中初始化
    fun init(context: Context)
}
