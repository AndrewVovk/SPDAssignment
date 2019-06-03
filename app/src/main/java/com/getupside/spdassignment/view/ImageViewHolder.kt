package com.getupside.spdassignment.view

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.getupside.spdassignment.R
import com.getupside.spdassignment.viewmodel.ImageState

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val bitmapObserver = Observer<Bitmap?> { bitmap ->
        val imageView = itemView.findViewById<ImageView>(R.id.image)
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
        } else {
            imageView.setImageResource(R.drawable.ic_error_outline_red_48dp)
        }
    }

    private var onRelease: (() -> Unit)? = null

    fun bind(lifecycleOwner: LifecycleOwner, state: ImageState) {
        state.bitmap.observe(lifecycleOwner, bitmapObserver)
        onRelease = {
            state.bitmap.removeObserver(bitmapObserver)
        }
    }

    fun destroy() {
        onRelease?.invoke()
        itemView.findViewById<ImageView>(R.id.image).setImageBitmap(null)
    }
}