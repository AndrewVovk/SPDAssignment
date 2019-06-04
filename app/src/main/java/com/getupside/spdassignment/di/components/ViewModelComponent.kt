package com.getupside.spdassignment.di.components

import com.getupside.spdassignment.di.modules.CacheDirModule
import com.getupside.spdassignment.di.modules.NetworkModule
import com.getupside.spdassignment.model.repository.network.NetworkManager
import com.getupside.spdassignment.viewmodel.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CacheDirModule::class, NetworkModule::class])
interface ViewModelComponent {

    fun provideNetworkManager(): NetworkManager

    fun injectViewModel(mainViewModel: MainViewModel)
}