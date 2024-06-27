package com.example.rssreader.network

import com.example.rssreader.model.NewsItems
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsItemsApiService {
    @GET("v2/top-headlines")
    suspend fun getNewsItems(
        @Query("country") countryName: String,
        @Query("apiKey") apiKey: String
    ): NewsItems
}