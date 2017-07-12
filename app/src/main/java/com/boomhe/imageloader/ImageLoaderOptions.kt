package com.boomhe.imageloader

import android.view.View
import com.bumptech.glide.request.target.BaseTarget

/**
 * Created by boomhe on 2017/7/12.
 * 加载图片 非必要选项
 *
 */
class ImageLoaderOptions {

    private var viewContainer: View? = null         // 图片容器
    private var url: String = ""                    // 图片地址
    private var resource: Integer? = null           // 图片地址
    private var holderDrawable: Int? = null         // 设置展位图
    private var imageSize: ImageSize? = null        // 设置图片的大小
    private var errorDrawable: Int? = null          // 是否展示加载错误的图片
    private var asGif: Boolean = false              // 是否作为gif展示
    private var isCrossFade: Boolean = true         // 是否渐变平滑的显示图片,默认为true
    private var blurImage: Boolean = false          // 是否使用高斯模糊
    private var target = null as BaseTarget<*>      // target
    private var mDiskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.DEFAULT  //磁盘缓存策略





    class ImageSize(width: Int,height: Int){
        private var width = 0
        private var height = 0

    }

    //对应磁盘缓存策略
    enum class DiskCacheStrategy{
        All,NONE,SOURCE,RESULT,DEFAULT
    }
}