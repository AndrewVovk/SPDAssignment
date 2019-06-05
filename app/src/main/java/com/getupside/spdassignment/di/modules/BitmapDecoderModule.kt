package com.getupside.spdassignment.di.modules

import com.getupside.spdassignment.viewmodel.BitmapDecoder
import dagger.Module
import dagger.Provides

@Module
class BitmapDecoderModule {

    @Provides
    fun provideBitmapDecoder() = BitmapDecoder()
}