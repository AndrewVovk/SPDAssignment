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
        val progressBar = findViewById<View>(R.id.progress_bar)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        viewModel.data.state.observe(this, Observer { state ->
            when (state) {
                ImageDataState.LOADING -> {
                    progressBar.visibility = VISIBLE
                    recyclerView.visibility = GONE
                }
                ImageDataState.DATA_CREATED -> {
                    progressBar.visibility = GONE
                    recyclerView.visibility = VISIBLE
                    recyclerView.adapter = SimpleAdapter(
                        { viewModel.data.size },
                        { ImageViewHolder(layoutInflater.inflate(R.layout.item_image, it, false)) },
                        { holder, position -> holder.bind(this, viewModel.data[position]) },
                        ImageViewHolder::destroy
                    )
                }
                ImageDataState.DATA_ADDED -> {
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }

        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}
