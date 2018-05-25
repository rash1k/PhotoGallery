package com.example.cherkassy.galleryphoto.di.module

import com.example.cherkassy.galleryphoto.common.manager.NetworkManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

//Для предоставления MyFragmentManager
@Module()
 class ManagerModule {



    @Singleton
    @Provides
    fun provideMyNetworkManager(): NetworkManager {
        return NetworkManager()
    }

  /*  @Singleton
    @Provides
    fun provideMyPreferenceManager(context: Application): MyPreferenceManager {
        return MyPreferenceManager(context)
    }*/
}