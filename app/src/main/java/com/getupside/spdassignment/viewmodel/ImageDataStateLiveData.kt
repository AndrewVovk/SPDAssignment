package com.getupside.spdassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.getupside.spdassignment.model.PagedListState
import com.getupside.spdassignment.model.PagedListStatus

class ImageDataStateLiveData(
    private val pagedListState: LiveData<PagedListState>,
    private val loadMore: LiveData<Any?>
) : LiveData<ImageDataState>() {

    private val pagedListStateObserver = Observer<PagedListState> {
        when (it?.status) {
            PagedListStatus.WAITING_FOR_FIRST_PAGE -> postValue(ImageDataState.LOADING)
            PagedListStatus.FIRST_PAGE_ADDED -> {
                if (it.itemsAdded > 0) {
                    postValue(ImageDataState.DATA_CREATED)
                } else {
                    postValue(ImageDataState.NO_RESULTS)
                }
            }
            PagedListStatus.NEXT_PAGE_ADDED -> {
                if (value == null) {
                    postValue(ImageDataState.DATA_CREATED)
                } else {
                    postValue(ImageDataState.DATA_ADDED)
                }
            }
        }
    }

    private val loadingMoreObserver = Observer<Any?> {
        if (value != ImageDataState.LOADING)
            postValue(ImageDataState.LOADING_MORE)
    }

    override fun onActive() {
        loadMore.observeForever(loadingMoreObserver)
        pagedListState.observeForever(pagedListStateObserver)
    }

    override fun onInactive() {
        pagedListState.removeObserver(pagedListStateObserver)
        loadMore.removeObserver(loadingMoreObserver)
    }
}