package com.example.cherkassy.galleryphoto.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton



@Module
class AppModule(private val mApplication: Application) {


    @Singleton
    @Provides
    fun provideContext(): Application {
        return mApplication
    }
}