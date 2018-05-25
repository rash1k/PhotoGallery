package com.example.cherkassy.galleryphoto

import android.app.Application
import com.example.cherkassy.galleryphoto.di.component.ApplicationComponent
import com.example.cherkassy.galleryphoto.di.component.DaggerApplicationComponent
import com.example.cherkassy.galleryphoto.di.module.AppModule


class MyApplication : Application() {

    companion object {
        @JvmStatic  //platformStatic allow access it from java code
        lateinit var sApplicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        //init Dagger 2
        buildComponent()

    }

    private fun buildComponent() {
        sApplicationComponent =
                DaggerApplicationComponent.builder()
                        .appModule(AppModule(this))
                        .build()
    }
}