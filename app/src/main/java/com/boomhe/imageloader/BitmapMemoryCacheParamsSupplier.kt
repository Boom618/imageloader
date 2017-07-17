package com.boomhe.imageloader

import android.os.Build
import com.facebook.common.internal.Supplier
import com.facebook.imagepipeline.cache.MemoryCacheParams
import android.app.ActivityManager
import com.facebook.common.util.ByteConstants


/**
 * Created by boomhe on 2017/7/14.
 *
 * 释放内存
 */
class BitmapMemoryCacheParamsSupplier : Supplier<MemoryCacheParams> {


    private val MAX_CACHE_ENTRIES = 56
    private val MAX_CACHE_ASHM_ENTRIES = 128
    private val MAX_CACHE_EVICTION_SIZE = 5
    private val MAX_CACHE_EVICTION_ENTRIES = 5
    private var mActivityManager: ActivityManager? = null

    constructor(activityManager: ActivityManager){
        this.mActivityManager = activityManager
    }


    override fun get(): MemoryCacheParams {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val params = MemoryCacheParams(getMaxCacheSize(),MAX_CACHE_ENTRIES,
                    MAX_CACHE_EVICTION_SIZE, MAX_CACHE_EVICTION_ENTRIES, 1)
            return params
        }else{
            val params = MemoryCacheParams(getMaxCacheSize(), MAX_CACHE_ASHM_ENTRIES,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE)
            return params
        }
    }

    /**
     * 获取最大内存
     */
    fun getMaxCacheSize(): Int {
        val memory = mActivityManager!!.memoryClass * ByteConstants.MB
        val maxMemory = Math.min(memory, Int.MAX_VALUE)
        if (maxMemory < 32 * ByteConstants.MB){
            return 4 * ByteConstants.MB
        }else if (maxMemory < 64 * ByteConstants.MB){
            return 6 * ByteConstants.MB
        }else{
            return maxMemory / 5
        }
    }
}