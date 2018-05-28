package com.example.cherkassy.galleryphoto.common.manager

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.cherkassy.galleryphoto.MyApplication
import javax.inject.Inject

class NetworkManager {
    companion object {
        val TAG = "NetworkManager"
    }

    @Inject
    lateinit var context: Application

    init {
        MyApplication.sApplicationComponent.inject(this)
    }


    fun isNetworkAccess(): Boolean {
        val cm =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null &&
                networkInfo.isConnected

    }
}



