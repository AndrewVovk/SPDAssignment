package com.getupside.spdassignment.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.os.Environment
import android.os.Environment.isExternalStorageRemovable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.getupside.spdassignment.model.PagedList
import com.getupside.spdassignment.model.SingleLiveEvent
import com.getupside.spdassignment.model.repository.Repository
import com.getupside.spdassignment.model.repository.network.GetGalleries
import com.getupside.spdassignment.model.repository.network.NetworkManager
import com.getupside.spdassignment.model.repository.network.data.Data
import java.io.File


class MainViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val DISK_CACHE_SUBDIR = "thumbnails"
    }

    private val diskCacheDir by lazy {
        val cachePath =
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
                || !isExternalStorageRemovable()
            ) {
                getApplication<Application>().externalCacheDir?.path
            } else {
                getApplication<Application>().cacheDir.path
            }

        File(cachePath + File.separator + DISK_CACHE_SUBDIR)
    }

    val error = SingleLiveEvent<String>()

    private val bitmapDecoder = BitmapDecoder()

    private val repository = Repository(diskCacheDir, bitmapDecoder::decode, error::setValue)

    private val networkManager = NetworkManager.instance

    private val loadMore = SingleLiveEvent<Any?>()

    val data: ImageData = createImageData(createList())

    override fun onCleared() {
        repository.close()
    }

    private fun createImageData(pagedList: PagedList<ImageItem>) =
        object : ImageData {

            override val state = ImageDataStateLiveData(pagedList.state, loadMore)
            override val size: Int get() = pagedList.size

            override fun get(position: Int) = object : ImageState {
                override val image = pagedList[position]
                override val bitmap = MutableLiveData<Bitmap?>().apply {
                    repository.getImage(pagedList[position], ::postValue)
                }
            }
        }

    private fun createList(): PagedList<ImageItem> {
        val list = PagedList<ImageItem>()

        val provider = GetGalleries(
            networkManager,
            { items, isFirstPage ->
                list.addPage(transform(items), isFirstPage, false)
            }, error::setValue
        )

        list.onGetItem = { position, size ->
            if (position == size - 4) {
                loadMore.call()
                provider.keepSearching()
            }
        }

        return list
    }

    private fun transform(galleries: List<Data>) =
        galleries
            .flatMap { it.images ?: emptyList() }
            .filter { it.type != "video/mp4" }
            .map { ImageItem(it.id.toLowerCase(), it.link) }
}