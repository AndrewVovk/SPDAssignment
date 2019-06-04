package com.getupside.spdassignment.di.components

import com.getupside.spdassignment.di.modules.*
import com.getupside.spdassignment.viewmodel.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CacheModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        BitmapDecoderModule::class,
        ConnectivityLiveDataModule::class
    ]
)
interface ViewModelComponent {
    fun inject(mainViewModel: MainViewModel)
}