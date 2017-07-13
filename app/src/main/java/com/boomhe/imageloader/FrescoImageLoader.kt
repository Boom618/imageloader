package com.boomhe.imageloader

import android.annotation.TargetApi
import android.app.ActivityManager
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.bumptech.glide.request.target.SimpleTarget
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.producers.BitmapMemoryCacheProducer
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder

/**
 * Created by boomhe on 2017/7/13.
 *  Fresco 图片加载库
 */
class FrescoImageLoader : IImageLoaderstrategy {

    override fun init(context: Context) {
        Fresco.initialize(context, getPipelineConfig(context))
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun showImage(options: ImageLoaderOptions) {
        showImageDrawee(options)
    }

    override fun cleanMemory(context: Context) {
    }

    fun getPipelineConfig(context: Context): ImagePipelineConfig {

        val diskCacheConfig = DiskCacheConfig.newBuilder(context)
                .setMaxCacheSize(30 * 1024 * 1024)
                .setMaxCacheSizeOnLowDiskSpace(5 * 1024 * 1024)
                .build()

        val config = ImagePipelineConfig.newBuilder(context)
                .setDownsampleEnabled(true)
                // 设置缓存
                .setMainDiskCacheConfig(diskCacheConfig)
                // 保证缓存达到一定条件就及时清除缓存
                .setBitmapMemoryCacheParamsSupplier(null)
                .build()
        return config
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun showImageDrawee(options: ImageLoaderOptions) {
        val view = options.viewContainer
        var drawee: SimpleDraweeView? = null
        var clazz = null as Class<*>
        var hierarchy: GenericDraweeHierarchy? = null
        val hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(view!!.context.resources)
        if (view is SimpleDraweeView) {
            drawee = view
            hierarchy = drawee.hierarchy
        } else if (view is ImageView) {
            clazz = ImageView::class.java
            drawee = getDraweeView(view,clazz)
        }else{
            Log.d("FrescoImageLoader"," 不支持的图片类型")
            return
        }

        if (drawee != null) {
            var uri = Uri.parse(options.url)
            if (!options.url.contains("http")) {
                uri = Uri.parse("fill://" + options.url)
            }

            if (options.holderDrawable != -1) {
                val holderDrawable = options.holderDrawable
                hierarchyBuilder.placeholderImage
            }

            if (hierarchy == null) {
                hierarchy = hierarchyBuilder.build()
            }

            drawee.hierarchy = hierarchy

            val controllerBuilder = Fresco.newDraweeControllerBuilder()
                    .setUri(uri).setAutoPlayAnimations(true)
            val imageBuilder = ImageRequestBuilder.newBuilderWithSource(uri)
            if (options.imageSize != null) {
                val widthSize = getSize(options.imageSize!!.width, view)
                val heightSize = getSize(options.imageSize!!.height, view)
                imageBuilder.resizeOptions = ResizeOptions(widthSize,heightSize)
            }

            val request = imageBuilder.build() as ImageRequest
            controllerBuilder.imageRequest = request

            val build = controllerBuilder.build() as DraweeController
            drawee.controller = build

        }


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun getDraweeView(viewContainer: View, classType: Class<*>): SimpleDraweeView {
        if (viewContainer is SimpleDraweeView) {
            return viewContainer
        }
        var mDraweeView: SimpleDraweeView? = null
        if (classType.isInstance(viewContainer)) {
            val layout = FrameLayout(viewContainer.context)
            if (viewContainer.parent is FrameLayout){
                val parent = viewContainer.parent as FrameLayout
                val params = viewContainer.layoutParams as FrameLayout.LayoutParams
                layout.layoutParams = params
                mDraweeView = exchangeChilde(parent, viewContainer, params)
            }else if (viewContainer.parent is RelativeLayout){
                val parent = viewContainer.parent as RelativeLayout
                val params = viewContainer.layoutParams as RelativeLayout.LayoutParams
                layout.layoutParams = params
                mDraweeView = exchangeChilde(parent,viewContainer,params)
            }else if (viewContainer.parent is LinearLayout){
                val parent = viewContainer.parent as LinearLayout
                val params = viewContainer.layoutParams as LinearLayout.LayoutParams
                layout.layoutParams = params
                exchangeView(parent,viewContainer,layout)
                layout.addView(viewContainer)
                mDraweeView = exchangeChilde(layout,viewContainer,params)
            }else{
                // 项目基本只使用了上面三种布局
                Log.d("FrescoImageLoader:"," 不支持的父布局 ")
            }
        }
        return mDraweeView!!
    }

    /*
     * 将ViewGroup中的一个childView移除，在同原样的位置添加一个新的View
     */
    fun exchangeView(parent: ViewGroup, viewOld: View, viewNew: View) {
        for (i in 0..parent.childCount) {
            if (parent.getChildAt(i).equals(viewOld)) {
                parent.removeView(viewOld)
                parent.addView(viewNew, i)
                return
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun exchangeChilde(parent: ViewGroup, testImageView: View, layoutParams: ViewGroup.LayoutParams): SimpleDraweeView {
        var draweeview: SimpleDraweeView? = null
        for (i in 0..parent.childCount) {
            if (testImageView.equals(parent.getChildAt(i))) {
                if (testImageView is ImageView) {
                    var img = testImageView
                    img.background = null
                    img.setImageDrawable(null)
                }

                if (i + 1 < parent.childCount) {
                    val view = parent.getChildAt(i + 1)
                    if (view is SimpleDraweeView) {
                        return view
                    }
                }

                draweeview = SimpleDraweeView(testImageView.context)
                draweeview.layoutParams = layoutParams
                parent.addView(draweeview, i + 1)
                return draweeview
            }
        }
        return draweeview!!
    }


    // 获取资源尺寸
    fun getSize(resSize: Int,container: View): Int {
        if (resSize <= 0){
            return SimpleTarget.SIZE_ORIGINAL
        }else{
            try {
               val offset = container.context.resources.getDimensionPixelOffset(resSize)
               return offset
            } catch(e: Resources.NotFoundException) {
                e.printStackTrace()
                return resSize
            }
        }
    }
}