package com.example.rssreader.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.rssreader.model.NewsItems

@Composable
fun HomeScreen(itemsViewModel: ItemsViewModel) {
    when (itemsViewModel.itemsUiState) {
        is ItemsUiState.Loading -> LoadingScreen()
        is ItemsUiState.Success -> ResultScreen(
            (itemsViewModel.itemsUiState as ItemsUiState.Success).news
        )
        is ItemsUiState.Error -> ErrorScreen(itemsViewModel)
    }
}

@Composable
fun ResultScreen(newsItems: NewsItems) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        newsItems.items.forEach { news ->
            val context = LocalContext.current

            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.link))
                context.startActivity(intent)
            }) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(news.title)
                    Text(news.pubDate)
                    Text(news.link)
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Loading")
    }
}

@Composable
fun ErrorScreen(itemsViewModel: ItemsViewModel) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Error")
            Button(onClick = { itemsViewModel.getItems(0) }) {
                Text("Retry")
            }
        }

    }

}
