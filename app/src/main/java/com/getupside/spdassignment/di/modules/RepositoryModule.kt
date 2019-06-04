package com.getupside.spdassignment.di.modules

import com.getupside.spdassignment.model.repository.Repository
import com.getupside.spdassignment.model.repository.cache.DiskCache
import com.getupside.spdassignment.model.repository.cache.MemoryCache
import com.getupside.spdassignment.model.repository.network.NetworkManager
import com.getupside.spdassignment.viewmodel.BitmapDecoder
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Singleton

@Module
class RepositoryModule {

    @JvmSuppressWildcards
    @Provides
    @Singleton
    fun provideRepository(
        networkManager: NetworkManager,
        bitmapDecoder: BitmapDecoder,
        memoryCache: MemoryCache,
        diskCache: DiskCache,
        onNetworkError: (String?) -> Unit
    ) =
        Repository(
            networkManager,
            bitmapDecoder,
            memoryCache,
            diskCache,
            onNetworkError
        )
}