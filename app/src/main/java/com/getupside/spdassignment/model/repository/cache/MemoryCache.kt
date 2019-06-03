package com.getupside.spdassignment.model.repository.cache

import android.graphics.Bitmap
import androidx.collection.LruCache

class MemoryCache {

    private val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
    private val cacheSize = maxMemory / 4

    private val memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
        override fun sizeOf(id: String, bitmap: Bitmap) = bitmap.byteCount / 1024
    }

    operator fun get(id: String) = memoryCache.get(id)

    operator fun set(id: String, bitmap: Bitmap) = memoryCache.put(id, bitmap)
}