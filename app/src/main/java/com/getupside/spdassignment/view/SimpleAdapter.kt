package com.getupside.spdassignment.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SimpleAdapter<VH>(private val getSize: () -> Int,
                        private val onInflate: (parent: ViewGroup) -> VH,
                        private val onBind: (VH, Int) -> Unit,
                        private val onDestroy: (VH) -> Unit)

    : RecyclerView.Adapter<VH>()
        where VH : RecyclerView.ViewHolder {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return onInflate(parent)
    }

    override fun getItemCount(): Int {
        return getSize()
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBind(holder, position)
    }

    override fun onViewRecycled(holder: VH) {
        onDestroy(holder)
    }
}