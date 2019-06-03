package com.getupside.spdassignment.model.repository.network

import com.getupside.spdassignment.model.repository.network.data.Data
import com.getupside.spdassignment.model.repository.network.data.GalleriesResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream


class NetworkManager private constructor() {
    companion object {
        private const val GALLERIES_URL = "https://api.imgur.com/3/"
        val instance by lazy { NetworkManager() }
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(GALLERIES_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val imgurAPI by lazy { retrofit.create(ImgurAPI::class.java) }

    fun getGalleries(
        page: Int,
        onDataReceived: (items: List<Data>) -> Unit,
        onError: (message: String?) -> Unit
    ) {
        imgurAPI.getGalleries(page).enqueue(
            object : Callback<GalleriesResponse> {
                override fun onResponse(call: Call<GalleriesResponse>, response: Response<GalleriesResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            onDataReceived(it.data)
                        }
                    } else {
                        onError(response.errorBody()?.string())
                    }

                }

                override fun onFailure(call: Call<GalleriesResponse>, t: Throwable) = onError(t.message)
            }
        )
    }

    fun getImage(
        url: String,
        onInputStream: (InputStream) -> Unit,
        onError: (message: String?) -> Unit
    ) {
        imgurAPI.getImage(url).enqueue(
            object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            onInputStream(it.byteStream())
                        }
                    } else {
                        onError(response.errorBody()?.string())
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) = onError(t.message)
            }
        )
    }
}