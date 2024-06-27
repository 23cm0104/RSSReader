package com.example.rssreader.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsItems(
    val articles: List<Item>
)

@Serializable
data class Item(
    val title: String,
    val description: String? = "",
    val urlToImage: String? = "",
    val publishedAt: String,
    @SerialName(value = "url")
    val link: String
)