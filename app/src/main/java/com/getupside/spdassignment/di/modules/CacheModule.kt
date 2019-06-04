package com.getupside.spdassignment.di.modules

import android.content.Context
import android.os.Environment
import com.getupside.spdassignment.model.repository.cache.DiskCache
import com.getupside.spdassignment.model.repository.cache.MemoryCache
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Singleton

@Module
class CacheModule(private val context: Context) {

    companion object {
        private const val DISK_CACHE_SUBDIR = "thumbnails"
    }

    @Provides
    @Singleton
    fun provideDiskCacheDir(): File {
        val cachePath =
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
                || !Environment.isExternalStorageRemovable()
            ) {
                context.externalCacheDir?.path
            } else {
                context.cacheDir.path
            }

        return File(cachePath + File.separator + DISK_CACHE_SUBDIR)
    }

    @Provides
    @Singleton
    fun provideMemoryCache() = MemoryCache()

    @Provides
    @Singleton
    fun provideDiskCache(diskCacheDir: File) = DiskCache(diskCacheDir)
}