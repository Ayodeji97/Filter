package com.mylearning.devplacement

import android.app.Application
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())

            // Enabling database for resume support even after the application is killed:
            val config = PRDownloaderConfig.newBuilder()
                    .setDatabaseEnabled(true)
                    .setReadTimeout(30000)
                    .setConnectTimeout(30000)
                    .build()
            PRDownloader.initialize(applicationContext, config)

        }
    }
}