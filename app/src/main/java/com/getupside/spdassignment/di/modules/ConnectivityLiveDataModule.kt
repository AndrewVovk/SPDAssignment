package com.getupside.spdassignment.di.modules

import android.util.Log
import com.getupside.spdassignment.viewmodel.ConnectivityLiveData
import com.getupside.spdassignment.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class ConnectivityLiveDataModule {

    @Provides
    fun provideOnNetworkErrorHandler(
        connectivityLiveData: ConnectivityLiveData
    ): (String?) -> Unit = {
        Log.e(MainViewModel::class.java.simpleName, it)
        connectivityLiveData.onError()
    }

    @Provides
    fun provideConnectivityLiveData() = ConnectivityLiveData()
}