package com.example.rssreader.network

import com.example.rssreader.model.NewsItems
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsItemsApiService {
    @GET("v1/api.json")
    suspend fun getNewsItems(
        @Query("rss_url") webUrl: String
    ): NewsItems
}