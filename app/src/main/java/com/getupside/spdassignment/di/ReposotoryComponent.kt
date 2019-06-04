package com.getupside.spdassignment.di

import com.getupside.spdassignment.model.repository.Repository
import com.getupside.spdassignment.model.repository.network.NetworkManager
import com.getupside.spdassignment.viewmodel.MainViewModel
import dagger.Component

@ApplicationScope
@Component(modules = [NetworkModule::class])
interface ReposotoryComponent {

    fun provideNetworkManager(): NetworkManager

    fun injectRepository(repository: Repository)

    fun injectViewModel(mainViewModel: MainViewModel)
}