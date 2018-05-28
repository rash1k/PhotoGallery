package com.example.cherkassy.galleryphoto.di.module

import com.example.cherkassy.galleryphoto.common.AppExecutor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module()
class ExecutorModule {

    @Singleton
    @Provides
    fun provideMyNetworkManager(): AppExecutor {
        return AppExecutor.getInstance()
    }
}