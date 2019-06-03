package com.getupside.spdassignment.view

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.getupside.spdassignment.R
import com.getupside.spdassignment.viewmodel.ImageDataState
import com.getupside.spdassignment.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        val mainProgressBar = findViewById<View>(R.id.progress_bar_main)
        val lazyProgressBar = findViewById<View>(R.id.progress_bar_lazy)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        viewModel.data.state.observe(this, Observer { state ->
            when (state) {
                ImageDataState.LOADING -> {
                    mainProgressBar.visibility = VISIBLE
                }
                ImageDataState.DATA_CREATED -> {
                    mainProgressBar.visibility = GONE
                    recyclerView.adapter = SimpleAdapter(
                        { viewModel.data.size },
                        { ImageViewHolder(layoutInflater.inflate(R.layout.item_image, it, false)) },
                        { holder, position -> holder.bind(this, viewModel.data[position]) },
                        ImageViewHolder::destroy
                    )
                }
                ImageDataState.LOADING_MORE -> {
                    lazyProgressBar.visibility = VISIBLE
                }
                ImageDataState.DATA_ADDED -> {
                    lazyProgressBar.visibility = GONE
                }
                else -> mainProgressBar.visibility = GONE
            }
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}
