package com.getupside.spdassignment.model.repository

import android.graphics.Bitmap
import com.getupside.spdassignment.model.repository.cache.DiskCache
import com.getupside.spdassignment.model.repository.cache.MemoryCache
import com.getupside.spdassignment.model.repository.network.NetworkManager
import com.getupside.spdassignment.viewmodel.BitmapDecoder
import com.getupside.spdassignment.viewmodel.ImageItem
import javax.inject.Inject


class Repository @Inject constructor(
    private val networkManager: NetworkManager,
    private val bitmapDecoder: BitmapDecoder,
    private val memoryCache: MemoryCache,
    private val diskCache: DiskCache,
    private val onError: (String?) -> Unit
) {

    fun getImage(imageItem: ImageItem, onBitmap: (Bitmap?) -> Unit) {

        val bitmapFromMemoryCache = memoryCache[imageItem.id]

        if (bitmapFromMemoryCache != null) {
            onBitmap(bitmapFromMemoryCache)
        } else {
            diskCache.get(imageItem.id) {
                if (it != null) {
                    memoryCache[imageItem.id] = it
                    onBitmap(it)
                } else {
                    networkManager.getImage(imageItem.url, { inputStream ->
                        bitmapDecoder.decode(inputStream) { bitmap ->
                            bitmap?.let {
                                diskCache[imageItem.id] = bitmap
                                memoryCache[imageItem.id] = bitmap
                                onBitmap(bitmap)
                            }
                        }
                    }, onError)
                }
            }
        }
    }

    fun close() = diskCache.close()
}