package com.getupside.spdassignment.model.repository.cache

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.jakewharton.disklrucache.DiskLruCache
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock


private const val DISK_CACHE_SIZE: Long = 1024 * 1024 * 50 // 50MB
private const val APP_VERSION = 1
private const val VALUE_COUNT = 1
private const val IO_BUFFER_SIZE = 8 * 1024
private const val QUALITY = 70

class DiskCache(diskCacheDir: File) {

    private var diskLruCache: DiskLruCache? = null
    private val diskCacheLock = ReentrantLock()
    private val diskCacheLockCondition: Condition = diskCacheLock.newCondition()
    private var diskCacheStarting = true

    init {
        thread {
            diskCacheLock.withLock {
                diskLruCache = DiskLruCache.open(
                    diskCacheDir,
                    APP_VERSION,
                    VALUE_COUNT,
                    DISK_CACHE_SIZE
                )
                diskCacheStarting = false
                diskCacheLockCondition.signalAll()
            }
        }
    }

    operator fun get(id: String, onBitmap: (Bitmap?) -> Unit) {
        thread {
            diskCacheLock.withLock {
                while (diskCacheStarting) {
                    try {
                        diskCacheLockCondition.await()
                    } catch (e: InterruptedException) {
                    }
                }
                onBitmap(diskLruCache?.get(id)?.let {
                    val inputStream = it.getInputStream(0)
                    val bufferedInputStream = BufferedInputStream(inputStream, 8 * 1024)
                    BitmapFactory.decodeStream(bufferedInputStream).also {
                        inputStream.close()
                        bufferedInputStream.close()
                    }
                })
            }
        }

    }

    operator fun set(id: String, bitmap: Bitmap) {
        thread {
            synchronized(diskCacheLock) {
                diskLruCache?.apply {
                    val editor = edit(id)
                    editor?.let {
                        if (writeBitmapToFile(bitmap, it)) {
                            diskLruCache?.flush()
                            editor.commit()
                        } else editor.abort()
                    }
                }
            }
        }
    }

    fun close() = diskLruCache?.close()

    private fun writeBitmapToFile(bitmap: Bitmap, editor: DiskLruCache.Editor): Boolean {
        return BufferedOutputStream(
            editor.newOutputStream(0),
            IO_BUFFER_SIZE
        ).run {
            bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, this).also {
                close()
            }
        }
    }
}