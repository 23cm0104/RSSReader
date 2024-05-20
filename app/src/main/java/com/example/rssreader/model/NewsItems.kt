package com.example.rssreader.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsItems(
    val items: List<Item>
)

@Serializable
data class Item(
    val title: String,
    val pubDate: String,
    val link: String
)