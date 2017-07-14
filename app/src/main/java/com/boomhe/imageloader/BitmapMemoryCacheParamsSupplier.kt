package com.boomhe.imageloader

import android.os.Build
import com.facebook.common.internal.Supplier
import com.facebook.imagepipeline.cache.MemoryCacheParams
import android.app.ActivityManager



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
    private val mActivityManager: ActivityManager? = null

    override fun get(): MemoryCacheParams {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val params = MemoryCacheParams(1,MAX_CACHE_ENTRIES,
                    MAX_CACHE_EVICTION_SIZE, MAX_CACHE_EVICTION_ENTRIES, 1)
            return params
        }else{
            val params = MemoryCacheParams(1, MAX_CACHE_ASHM_ENTRIES,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE)
            return params
        }
    }
}