package com.getupside.spdassignment.di.components

import com.getupside.spdassignment.di.modules.CacheDirModule
import com.getupside.spdassignment.di.modules.NetworkModule
import com.getupside.spdassignment.model.repository.Repository
import com.getupside.spdassignment.model.repository.network.NetworkManager
import com.getupside.spdassignment.viewmodel.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, CacheDirModule::class])
interface RepositoryComponent {

    fun provideNetworkManager(): NetworkManager

    fun injectRepository(repository: Repository)
}