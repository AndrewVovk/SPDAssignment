package com.getupside.spdassignment.view

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.getupside.spdassignment.R
import com.getupside.spdassignment.viewmodel.ImageDataState
import com.getupside.spdassignment.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    companion object {
        const val LAYOUT_MANAGER_STATE = "layoutManagerState"
    }

    private val viewModel by lazy { ViewModelProviders.of(this)[MainViewModel::class.java] }
    private val mainProgressBar by lazy { findViewById<View>(R.id.progress_bar_main) }
    private val lazyProgressBar by lazy { findViewById<View>(R.id.progress_bar_lazy) }
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }
    private val rootView by lazy { findViewById<View>(R.id.root) }
    private val adapter by lazy {
        SimpleAdapter(
            { viewModel.data.size },
            { ImageViewHolder(layoutInflater.inflate(R.layout.item_image, it, false)) },
            { holder, position -> holder.bind(this, viewModel.data[position]) },
            ImageViewHolder::destroy
        )
    }

    private val imageDataStateObserver = Observer<ImageDataState> { state ->
        when (state) {
            ImageDataState.LOADING -> {
                mainProgressBar.visibility = VISIBLE
            }
            ImageDataState.DATA_CREATED -> {
                mainProgressBar.visibility = GONE
                lazyProgressBar.visibility = GONE
                initRecyclerView()
            }
            ImageDataState.LOADING_MORE -> {
                lazyProgressBar.visibility = VISIBLE
            }
            ImageDataState.DATA_ADDED -> {
                mainProgressBar.visibility = GONE
                lazyProgressBar.visibility = GONE
                recyclerView.adapter?.notifyDataSetChanged() ?: initRecyclerView()
            }
            else -> mainProgressBar.visibility = GONE
        }
    }

    private fun initRecyclerView() {
        recyclerView.adapter = adapter
        layoutManagerState?.let { recyclerView.layoutManager?.onRestoreInstanceState(it) }
    }

    private val connectivityObserver = Observer<Boolean> {
        if (!it) {
            lazyProgressBar.visibility = GONE
            Snackbar
                .make(rootView, R.string.no_internet_connection, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) {
                    viewModel.retryToConnect()
                }.show()
        }
    }

    private var layoutManagerState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        layoutManagerState = savedInstanceState?.getParcelable(LAYOUT_MANAGER_STATE)

        viewModel.data.state.observe(this, imageDataStateObserver)
        viewModel.connectivityLiveData.observe(this, connectivityObserver)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(LAYOUT_MANAGER_STATE, recyclerView.layoutManager?.onSaveInstanceState())
    }
}
