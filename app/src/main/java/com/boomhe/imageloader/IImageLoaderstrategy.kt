package com.boomhe.imageloader

import android.content.Context

/**
 * Created by boomhe on 2017/7/12.
 * 加载图片 策略 接口
 */
interface IImageLoaderstrategy {

    // 在application的oncreate中初始化
    fun init(context: Context)

    fun showImage(options: ImageLoaderOptions)

    fun cleanMemory(context: Context)



}