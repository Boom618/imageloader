package com.boomhe.imageloader

import android.view.View
import com.bumptech.glide.request.target.BaseTarget

/**
 * Created by boomhe on 2017/7/12.
 * 加载图片 非必要选项
 *
 */
class ImageLoaderOptions {

    var viewContainer: View? = null         // 图片容器
    var url: String = ""                    // 图片地址
    var resource: Integer? = null           // 图片地址
    var holderDrawable: Int? = null         // 设置展位图
    var imageSize: ImageSize? = null        // 设置图片的大小
    var errorDrawable: Int? = null          // 是否展示加载错误的图片
    var asGif: Boolean = false              // 是否作为gif展示
    var isCrossFade: Boolean = true         // 是否渐变平滑的显示图片,默认为true
    var blurImage: Boolean = false          // 是否使用高斯模糊
    var target = null as BaseTarget<*>      // target
    var mDiskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.DEFAULT  //磁盘缓存策略

    constructor(builder: Builder) {
        this.viewContainer = builder.viewContainer
        this.url = builder.url
        this.resource = builder.resource
        this.holderDrawable = builder.holderDrawable
        this.imageSize = builder.imageSize
        this.errorDrawable = builder.errorDrawable
        this.asGif = builder.asGif
        this.isCrossFade = builder.isCrossFade
        this.blurImage = builder.blurImage
        this.target = builder.target
        this.mDiskCacheStrategy = builder.mDiskCacheStrategy
    }

    class Builder{
        var viewContainer: View? = null         // 图片容器
        var url: String = ""                    // 图片地址
        var resource: Integer? = null           // 图片地址
        var holderDrawable: Int? = null         // 设置展位图
        var imageSize: ImageSize? = null        // 设置图片的大小
        var errorDrawable: Int? = null          // 是否展示加载错误的图片
        var asGif: Boolean = false              // 是否作为gif展示
        var isCrossFade: Boolean = true         // 是否渐变平滑的显示图片,默认为true
        var blurImage: Boolean = false          // 是否使用高斯模糊
        var target = null as BaseTarget<*>      // target
        var mDiskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.DEFAULT  //磁盘缓存策略
    }



    class ImageSize(width: Int,height: Int){
        var width = 0
        var height = 0

    }

    //对应磁盘缓存策略
    enum class DiskCacheStrategy{
        All,NONE,SOURCE,RESULT,DEFAULT
    }
}