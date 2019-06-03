package com.getupside.spdassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.getupside.spdassignment.model.PagedListState
import com.getupside.spdassignment.model.PagedListStatus

class ImageDataStateLiveData(private val pagedListState: LiveData<PagedListState>)
    : LiveData<ImageDataState>() {

    private val observer = Observer<PagedListState> {
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

    override fun onActive() {
        pagedListState.observeForever(observer)
    }

    override fun onInactive() {
        pagedListState.removeObserver(observer)
    }
}