package com.getupside.spdassignment.model.repository.network

import com.getupside.spdassignment.model.repository.network.data.Data
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

class GetGalleries(networkManager: NetworkManager,
                   onDataReceived: (items: List<Data>, isFirstPage: Boolean) -> Unit,
                   onError: (message: String?) -> Unit) {

    private val keepGoing = LinkedBlockingQueue<Boolean>(1)

    init {
        thread {
            var page = 0
            var hasMoreData = true
            val populate: (List<Data>) -> Unit = { items ->
                onDataReceived(items, page == 0)
                hasMoreData = items.isNotEmpty()
                if (hasMoreData) page++
            }

            do {
                networkManager.getGalleries(page, populate, onError)

            } while (hasMoreData && keepGoing.take())
        }
    }

    fun keepSearching() {
        keepGoing.offer(true)
    }

    fun release() {
        keepGoing.offer(false)
    }
}