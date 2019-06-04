package com.getupside.spdassignment.di.modules

import android.util.Log
import com.getupside.spdassignment.viewmodel.ConnectivityLiveData
import com.getupside.spdassignment.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ConnectivityLiveDataModule {

    @Provides
    @Singleton
    fun provideOnNetworkErrorHandler(
        connectivityLiveData: ConnectivityLiveData
    ): (String?) -> Unit = {
        Log.e(MainViewModel::class.java.simpleName, it)
        connectivityLiveData.onError()
    }

    @Provides
    @Singleton
    fun provideConnectivityLiveData() = ConnectivityLiveData()
}