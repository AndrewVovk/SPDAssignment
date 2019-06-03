package com.getupside.spdassignment.model

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

enum class PagedListStatus { WAITING_FOR_FIRST_PAGE, FIRST_PAGE_ADDED, NEXT_PAGE_ADDED }

interface PagedListState {
    val status: PagedListStatus
    val itemsAdded: Int
    val isLastPage: Boolean
}

class PagedList<T> {

    private val list = mutableListOf<T>()
    private val handler = Handler()
    private val internalState = MutableLiveData<PagedListState>()

    val state: LiveData<PagedListState> get() = internalState
    var onGetItem: ((position: Int, size: Int) -> Unit)? = null

    init {
        internalState.value = object : PagedListState {
            override val status = PagedListStatus.WAITING_FOR_FIRST_PAGE
            override val itemsAdded = 0
            override val isLastPage = false
        }
    }

    fun addPage(items: Iterable<T>, isFirstPage: Boolean, isLastPage: Boolean) {
        list += items
        val state = object : PagedListState {
            override val status = if (isFirstPage) PagedListStatus.FIRST_PAGE_ADDED else PagedListStatus.NEXT_PAGE_ADDED
            override val itemsAdded = items.count()
            override val isLastPage = isLastPage
        }
        handler.post {
            size = list.size
            internalState.value = state
        }
    }

    var size: Int = 0
        private set

    operator fun get(position: Int): T {
        onGetItem?.invoke(position, size)
        return list[position]
    }
}