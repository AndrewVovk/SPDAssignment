package com.getupside.spdassignment

import android.graphics.Bitmap
import com.getupside.spdassignment.model.repository.Repository
import com.getupside.spdassignment.model.repository.cache.DiskCache
import com.getupside.spdassignment.model.repository.cache.MemoryCache
import com.getupside.spdassignment.model.repository.network.NetworkManager
import com.getupside.spdassignment.viewmodel.BitmapDecoder
import com.getupside.spdassignment.viewmodel.ImageItem
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.createinstance.createInstance
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    @Mock
    lateinit var networkManager: NetworkManager
    @Mock
    lateinit var bitmapDecoder: BitmapDecoder
    @Mock
    lateinit var memoryCache: MemoryCache
    @Mock
    lateinit var diskCache: DiskCache
    @Mock
    lateinit var onError: (String?) -> Unit
    @Mock
    lateinit var imageItem: ImageItem

    @InjectMocks
    lateinit var repository: Repository
    @Mock
    lateinit var onBitmap: (Bitmap?) -> Unit

    @Mock
    lateinit var bitmap: Bitmap

    @Test
    @Throws(InterruptedException::class)
    fun whenImageIsInMemoryCache() {
        //when Bitmap is in MemoryCache, then DiskCache and NetworkManager shouldn't be queried
        whenever(imageItem.id).thenReturn("")
        whenever(memoryCache[anyString()]).thenReturn(bitmap)
        whenever(diskCache.get(anyString(), any())).thenAnswer {
            (it.arguments[1] as (Bitmap?) -> Unit)(null)
        }

        repository.getImage(imageItem, onBitmap)

        verify(memoryCache)[""]
        verify(diskCache, never()).get(anyString(), any())
        verify(networkManager, never()).getImage(anyOrNull(), anyOrNull(), anyOrNull())
    }

    @Test
    @Throws(InterruptedException::class)
    fun whenImageIsInDiskCache() {
        //when Bitmap is in MemoryCache, then DiskCache and NetworkManager shouldn't be queried
        whenever(imageItem.id).thenReturn("")
        whenever(memoryCache[anyString()]).thenReturn(null)
        whenever(diskCache.get(anyString(), any())).thenAnswer {
            (it.arguments[1] as (Bitmap?) -> Unit)(bitmap)
        }

        repository.getImage(imageItem, onBitmap)

        verify(memoryCache)[""]
        verify(diskCache).get(anyString(), any())
        verify(networkManager, never()).getImage(anyOrNull(), anyOrNull(), anyOrNull())
    }

    @Test
    @Throws(InterruptedException::class)
    fun whenImageIsInNotCached() {
        whenever(imageItem.id).thenReturn("")
        whenever(memoryCache[anyString()]).thenReturn(null)
        whenever(diskCache.get(anyString(), any())).thenAnswer {
            (it.arguments[1] as (Bitmap?) -> Unit)(null)
        }

        repository.getImage(imageItem, onBitmap)

        verify(memoryCache)[""]
        verify(diskCache).get(anyString(), any())
        verify(networkManager).getImage(anyOrNull(), anyOrNull(), anyOrNull())
    }

    inline fun <reified T : Any> any(): T {
        return Mockito.any(T::class.java) ?: createInstance()
    }

}