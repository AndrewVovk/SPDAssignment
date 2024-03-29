package com.getupside.spdassignment.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.BufferedInputStream
import java.io.InputStream
import kotlin.concurrent.thread

class BitmapDecoder {
    fun decode(inputStream: InputStream, onBitmap: (Bitmap?) -> Unit) {
        thread {
            val bufferedInputStream = BufferedInputStream(inputStream)
            val options = evaluateSize(bufferedInputStream)
            bufferedInputStream.reset()
            val imageWidth = options.outWidth
            val imageHeight = options.outHeight
            options.inJustDecodeBounds = false
            options.inSampleSize = when {
                imageWidth * imageHeight > 16 * 1024 * 1024 -> 8
                imageWidth * imageHeight > 8 * 1024 * 1024 -> 4
                imageWidth * imageHeight > 4 * 1024 * 1024 -> 2
                else -> 1
            }
            onBitmap(BitmapFactory.decodeStream(bufferedInputStream, null, options))
        }
    }

    //evaluate image size without Bitmap allocation
    private fun evaluateSize(inputStream: InputStream): BitmapFactory.Options {
        inputStream.mark(inputStream.available())
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(inputStream, null, options)
        inputStream.reset()
        return options
    }
}