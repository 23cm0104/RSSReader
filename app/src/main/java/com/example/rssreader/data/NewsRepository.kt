package com.example.rssreader.data

import com.example.rssreader.model.NewsItems
import com.example.rssreader.network.NewsItemsApiService

interface NewsRepository {
    suspend fun getNewsItems(index: Int): NewsItems
}

class NetWorkNewsRepository(private val newsItemsApiService: NewsItemsApiService) : NewsRepository {
    private val urlList = listOf(
        "https://news.yahoo.co.jp/rss/topics/it.xml",
        "https://news.yahoo.co.jp/rss/topics/science.xml",
        "https://news.yahoo.co.jp/rss/topics/business.xml"
    )

    override suspend fun getNewsItems(index: Int): NewsItems = newsItemsApiService.getNewsItems(
        webUrl = urlList[index]
    )
}