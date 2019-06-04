package com.getupside.spdassignment.model.repository

import android.graphics.Bitmap
import com.getupside.spdassignment.model.repository.cache.DiskCache
import com.getupside.spdassignment.model.repository.cache.MemoryCache
import com.getupside.spdassignment.model.repository.network.ImgurAPI
import com.getupside.spdassignment.model.repository.network.NetworkManager
import com.getupside.spdassignment.viewmodel.ImageItem
import java.io.File
import java.io.InputStream
import javax.inject.Inject


class Repository @Inject constructor(
    private val networkManager: NetworkManager,
    diskCacheDir: File,
    private val decodeBitmap: (InputStream, (Bitmap?) -> Unit) -> Unit,
    private val onError: (String?) -> Unit
) {
    private val memoryCache = MemoryCache()
    private val diskCache = DiskCache(diskCacheDir)

    fun getImage(imageItem: ImageItem, onBitmap: (Bitmap?) -> Unit) {

        memoryCache[imageItem.id]?.let(onBitmap) ?: diskCache.get(imageItem.id) {
            it?.let { bitmap ->
                memoryCache[imageItem.id] = bitmap
                onBitmap(bitmap)
            } ?: networkManager.getImage(imageItem.url, { inputStream ->
                decodeBitmap(inputStream) { bitmap ->
                    bitmap?.let {
                        diskCache[imageItem.id] = bitmap
                        memoryCache[imageItem.id] = bitmap
                        onBitmap(bitmap)
                    }
                }
            }, onError)
        }
    }

    fun close() = diskCache.close()
}