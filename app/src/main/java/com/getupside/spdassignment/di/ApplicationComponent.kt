package com.getupside.spdassignment.di

import android.content.Context
import com.getupside.spdassignment.App
import com.getupside.spdassignment.model.repository.network.ImgurAPI
import com.getupside.spdassignment.model.repository.network.NetworkManager
import com.getupside.spdassignment.viewmodel.MainViewModel
import dagger.Component

@ApplicationScope
@Component(modules = [ContextModule::class, NetworkModule::class])
interface ApplicationComponent {

    fun provideApi(): ImgurAPI

    @ApplicationContext
    fun provideContext(): Context

    fun provideNetworkManager(): NetworkManager

    fun injectApplication(app: App)

    fun injectViewModel(mainViewModel: MainViewModel)
}