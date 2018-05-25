package com.example.cherkassy.galleryphoto.common.manager

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.cherkassy.galleryphoto.MyApplication
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject


//Проверяет имеет ли устройство доступ в интернет и доступна ли ссылка
class NetworkManager {

    @Inject
    lateinit var context: Application

    companion object {
        val TAG = "NetworkManager"
    }

    init {
        MyApplication.sApplicationComponent.inject(this)
    }


     fun isOnline(): Boolean {
        val cm =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null &&
                networkInfo.isConnected

    }

     fun isImageReachable(urlStr: String): Boolean {
        return try {
            when {
                !isOnline() -> false
                else -> {
                    val url = URL(urlStr)
                    val urlc = url.openConnection() as HttpURLConnection
                    urlc.connectTimeout = 2000
                    urlc.connect()
                   return urlc.responseCode == HttpURLConnection.HTTP_OK
                }
            }
        } catch (e: Exception) {
            false
        }
    }
}



