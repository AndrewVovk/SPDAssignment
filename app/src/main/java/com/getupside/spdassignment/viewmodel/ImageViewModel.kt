package com.getupside.spdassignment.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData

enum class ImageDataState { LOADING, NO_RESULTS, LOADING_MORE, DATA_CREATED, DATA_ADDED }

interface ImageData {
    val state: LiveData<ImageDataState>
    val size: Int
    operator fun get(position: Int): ImageState
}

interface ImageState {
    val image: ImageItem
    val bitmap: LiveData<Bitmap?>
}

data class ImageItem(val id: String, val url: String)