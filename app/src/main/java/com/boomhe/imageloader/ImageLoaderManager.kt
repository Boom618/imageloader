package com.boomhe.imageloader

import android.content.Context

/**
 * Created by boomhe on 2017/7/13.
 *
 */
class ImageLoaderManager: IImageLoaderstrategy {

    constructor()

    private var INSTANCE: ImageLoaderManager = ImageLoaderManager()

    fun getInstance(): ImageLoaderManager{
        if (INSTANCE == null) {
            synchronized(ImageLoaderManager::class.java){
                if (INSTANCE == null) {
                    INSTANCE = ImageLoaderManager()
                }
            }
        }
        return INSTANCE
    }

    override fun init(context: Context) {

    }

    override fun showImage(options: ImageLoaderOptions) {

    }

    override fun cleanMemory(context: Context) {

    }
}