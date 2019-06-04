package com.getupside.spdassignment.di.modules

import com.getupside.spdassignment.viewmodel.BitmapDecoder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BitmapDecoderModule {

    @Provides
    @Singleton
    fun provideBitmapDecoder() = BitmapDecoder()
}