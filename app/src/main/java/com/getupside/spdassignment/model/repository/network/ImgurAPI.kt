package com.getupside.spdassignment.model.repository.network

import com.getupside.spdassignment.model.repository.network.data.GalleriesResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ImgurAPI {

    @Headers("Authorization: Client-ID e4e73cc7af3e4cb")
    @GET("gallery/hot/viral/day/{page}")
    fun getGalleries(
        @Path("page") page: Int
    ): Call<GalleriesResponse>

    @GET
    fun getImage(
        @Url url: String
    ): Call<ResponseBody>
}