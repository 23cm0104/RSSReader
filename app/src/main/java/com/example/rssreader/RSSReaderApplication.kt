package com.example.rssreader

import android.app.Application
import com.example.rssreader.data.AppContainer
import com.example.rssreader.data.DefaultAppContainer

class RSSReaderApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}