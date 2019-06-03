package com.getupside.spdassignment.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

enum class ImageDataState { LOADING, NO_RESULTS, DATA_CREATED, DATA_ADDED }

interface ImageData {
    val state: LiveData<ImageDataState>
    val size: Int
//    operator fun get(position: Int): ImageViewModelFactory
    operator fun get(position: Int): ImageState
    fun getId(position: Int): String
}

interface ImageState {
    val image: ImageItem
    val bitmap: LiveData<Bitmap>
}

//class ImageViewModel(private val state: ImageState) : ViewModel() {
//    val image = state.image
//    val isLoaded = state.bitmap
//}
//
//
//class ImageViewModelFactory(private val getState: () -> ImageState) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return ImageViewModel(getState()) as T
//    }
//}

data class ImageItem(val id: String, val url: String)