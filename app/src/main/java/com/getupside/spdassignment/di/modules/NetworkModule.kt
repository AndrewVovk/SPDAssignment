package com.getupside.spdassignment.di.modules

import com.getupside.spdassignment.model.repository.network.ImgurAPI
import com.getupside.spdassignment.model.repository.network.NetworkManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class NetworkModule {

    companion object {
        private const val GALLERIES_URL = "https://api.imgur.com/3/"
    }

    @Provides
    fun provideImgurAPI(retrofit: Retrofit): ImgurAPI {
        return retrofit.create<ImgurAPI>(ImgurAPI::class.java)
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GALLERIES_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideNetworkManager(api: ImgurAPI): NetworkManager {
        return NetworkManager(api)
    }
}