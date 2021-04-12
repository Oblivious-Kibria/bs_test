package com.bs.androidtest.di

import android.app.Application
import com.bs.androidtest.di.component.AppComponent
import com.bs.androidtest.di.component.DaggerAppComponent
import com.bs.androidtest.di.module.AppModule
import com.bs.androidtest.di.module.NetworkModule
import com.bs.androidtest.networking.ApiConstants
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MyApplication: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
                .builder()
                .networkModule(NetworkModule(ApiConstants.BASE_URL))
                .appModule(AppModule(this))
                .build()
    }
}