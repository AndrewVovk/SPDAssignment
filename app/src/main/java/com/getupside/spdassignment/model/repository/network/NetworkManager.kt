package com.getupside.spdassignment.model.repository.network

import com.getupside.spdassignment.model.repository.network.data.Data
import com.getupside.spdassignment.model.repository.network.data.GalleriesResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkManager @Inject constructor(private val imgurAPI: ImgurAPI) {

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