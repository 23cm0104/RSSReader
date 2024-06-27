package com.example.rssreader.data

import com.example.rssreader.model.NewsItems
import com.example.rssreader.network.NewsItemsApiService

interface NewsRepository {
    suspend fun getNewsItems(index: Int): NewsItems
}

class NetWorkNewsRepository(private val newsItemsApiService: NewsItemsApiService) : NewsRepository {
    private val countryList = listOf(
        "jp",
        "us",
        "cn"
    )

    override suspend fun getNewsItems(index: Int): NewsItems = newsItemsApiService.getNewsItems(
        countryName = countryList[index],
        apiKey = "a3fe7d72262f43948cf2db2aa973200f"
    )
}