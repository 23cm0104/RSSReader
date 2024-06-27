package com.example.rssreader.data

import com.example.rssreader.network.NewsItemsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val newsRepository: NewsRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://newsapi.org/"
    private val jsonDC = Json { ignoreUnknownKeys = true }
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            jsonDC.asConverterFactory("application/json; charset=UTF8".toMediaType())
        )
        .baseUrl(baseUrl)
        .build()
    private val retrofitService: NewsItemsApiService by lazy {
        retrofit.create(NewsItemsApiService::class.java)
    }
    override val newsRepository: NewsRepository by lazy {
        NetWorkNewsRepository(retrofitService)
    }
}