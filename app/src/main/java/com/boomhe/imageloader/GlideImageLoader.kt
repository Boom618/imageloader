package com.boomhe.imageloader

import android.content.Context
import android.os.Looper
import android.widget.ImageView
import com.bumptech.glide.DrawableTypeRequest
import com.bumptech.glide.GenericRequestBuilder
import com.bumptech.glide.Glide

/**
 * Created by boomhe on 2017/7/14.
 *
 * Glide 图片加载库
 */
class GlideImageLoader : IImageLoaderstrategy {

    override fun init(context: Context) {

    }

    override fun showImage(options: ImageLoaderOptions) {

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
            if (options.asGif) {
                bitmap = manager.load(options.resource).asBitmap()
            } else {
                bitmap = manager.load(options.url).asBitmap()
            }
        }
        bitmap = manager.load(options.url).asBitmap()
        return bitmap
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

        return builder

    }

}