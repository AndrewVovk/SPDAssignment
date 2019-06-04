package com.getupside.spdassignment.di.components

import com.getupside.spdassignment.di.modules.BitmapDecoderModule
import com.getupside.spdassignment.di.modules.CacheModule
import com.getupside.spdassignment.di.modules.ConnectivityLiveDataModule
import com.getupside.spdassignment.di.modules.NetworkModule
import com.getupside.spdassignment.model.repository.Repository
import dagger.Component

@Component(
    modules = [
        NetworkModule::class,
        CacheModule::class,
        BitmapDecoderModule::class,
        ConnectivityLiveDataModule::class
    ]
)
interface RepositoryComponent {
    fun inject(repository: Repository)
}