package com.boomhe.imageloader

import android.content.Context
import android.os.Looper
import android.widget.ImageView
import com.bumptech.glide.DrawableTypeRequest
import com.bumptech.glide.GenericRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget

/**
 * Created by boomhe on 2017/7/14.
 *
 * Glide 图片加载库
 */
class GlideImageLoader : IImageLoaderstrategy {

    override fun init(context: Context) {

    }

    override fun showImage(options: ImageLoaderOptions) {
        val mGenericRequestBuilder = initOptions(options)
        if (mGenericRequestBuilder != null) {
            showImageLast(mGenericRequestBuilder,options)
        }
    }

    override fun cleanMemory(context: Context) {
        if (Looper.myLooper() === Looper.getMainLooper()) {
            Glide.get(context).clearMemory()
        }
    }

    fun initOptions(options: ImageLoaderOptions): GenericRequestBuilder<*,*,*,*> {

        val view = options.viewContainer
        val manager = Glide.with(view!!.context)
        var bitmap: GenericRequestBuilder<*,*,*,*>
        if (view is ImageView) {
            bitmap = manager.load(options.url)
            if (options.asGif) {
                bitmap = manager.load(options.resource).asBitmap()
            }
            bitmap = loadGenericParams(bitmap,options)
            return bitmap
        }
        return null!!
    }

    fun loadGenericParams(requestBuilder: GenericRequestBuilder<*,*,*,*>,options: ImageLoaderOptions): GenericRequestBuilder<*,*,*,*>{

        val view = options.viewContainer
        var builder = requestBuilder
        if (requestBuilder is DrawableTypeRequest) {
            if (options.isCrossFade) {
                requestBuilder.crossFade()
            }else if ( options.asGif){
                builder = requestBuilder.asGif()
            }
        }
        builder.skipMemoryCache(options.isSkipMemoryCache)

        if (options.imageSize != null) {
            val width = options.imageSize!!.width
            val height = options.imageSize!!.height
            builder.override(width,height)
        }

        if (options.holderDrawable != null) {
            builder.placeholder(options.holderDrawable)
        }

        if (options.errorDrawable != null) {
            builder.error(options.errorDrawable)
        }

        if (options.mDiskCacheStrategy != ImageLoaderOptions.DiskCacheStrategy.DEFAULT){
            when (options.mDiskCacheStrategy) {
                ImageLoaderOptions.DiskCacheStrategy.NONE -> {
                    builder.diskCacheStrategy(DiskCacheStrategy.NONE)
                }
                ImageLoaderOptions.DiskCacheStrategy.All ->{
                    builder.diskCacheStrategy(DiskCacheStrategy.ALL)
                }
                ImageLoaderOptions.DiskCacheStrategy.SOURCE ->{
                    builder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                }
                ImageLoaderOptions.DiskCacheStrategy.RESULT ->{
                    builder.diskCacheStrategy(DiskCacheStrategy.RESULT)
                }
            }
        }

        return builder

    }

    fun showImageLast(mDrawableTypeRequest: GenericRequestBuilder<*,*,*,*>,options: ImageLoaderOptions){

        val imageView = options.viewContainer as ImageView
        // 是否使用高斯模糊
        if (options.blurImage) {
            mDrawableTypeRequest.into(imageView)
            return
        }
        // 是否展示一个gif
        if (options.asGif) {
            mDrawableTypeRequest.dontAnimate().into(object :SimpleTarget<GifDrawable>(){
                override fun onResourceReady(resource: GifDrawable?, glideAnimation: GlideAnimation<in GifDrawable>?) {
                    imageView.setImageDrawable(resource)
                    resource!!.start()
                }
            } as ImageView)
            return
        }

        if (options.target != null) {
            mDrawableTypeRequest.into(options.target as ImageView)
            return
        }
        mDrawableTypeRequest.into(imageView)

    }

}