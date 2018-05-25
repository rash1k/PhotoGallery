package com.example.cherkassy.galleryphoto.di.component

import com.example.cherkassy.galleryphoto.common.manager.NetworkManager
import com.example.cherkassy.galleryphoto.di.module.AppModule
import com.example.cherkassy.galleryphoto.di.module.ManagerModule
import com.example.cherkassy.galleryphoto.ui.PhotoActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
//@Component(modules = [AppModule::class, ManagerModule::class, RestModule::class, ContextModule::class])
@Component(modules = [AppModule::class, ManagerModule::class])
interface ApplicationComponent {

    //Activities
    fun inject(mainActivity: PhotoActivity)

    //Managers
    fun inject(networkManager: NetworkManager)
}