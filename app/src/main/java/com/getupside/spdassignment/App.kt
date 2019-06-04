package com.getupside.spdassignment

import android.app.Application
import com.getupside.spdassignment.di.ContextModule
import com.getupside.spdassignment.di.DaggerApplicationComponent

class App : Application() {

    val applicationComponent by lazy{
        DaggerApplicationComponent.builder().contextModule(ContextModule(this)).build()
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent.injectApplication(this)
    }
}