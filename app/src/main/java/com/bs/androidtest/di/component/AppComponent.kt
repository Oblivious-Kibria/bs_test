package com.bs.androidtest.di.component

import com.bs.androidtest.di.module.AppModule
import com.bs.androidtest.di.module.NetworkModule
import com.bs.androidtest.di.module.ViewModelModule
import com.bs.androidtest.ui.fragments.GalleryFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, NetworkModule::class])
interface AppComponent {

    fun injectFragment(fragment: GalleryFragment)

}