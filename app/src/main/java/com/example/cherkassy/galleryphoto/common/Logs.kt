package com.example.cherkassy.galleryphoto.common

import android.util.Log

import com.example.cherkassy.galleryphoto.BuildConfig

object Logs {
    private val isLogsEnabled = BuildConfig.DEBUG

    fun d(tag: String, message: String) {
        if (isLogsEnabled) Log.d(tag, message)
    }
}
