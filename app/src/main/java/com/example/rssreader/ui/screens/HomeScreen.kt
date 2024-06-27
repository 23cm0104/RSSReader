package com.example.rssreader.ui.screens


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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rssreader.R
import com.example.rssreader.model.NewsItems
import com.example.rssreader.ui.components.NewsDetail
import com.example.rssreader.model.Item

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
    var isShowDetail by remember {
        mutableStateOf<Item?>(null)
    }

    isShowDetail?.let { NewsDetail(onDismiss = { isShowDetail = null }, newsItems = it) }


    Column(modifier = Modifier.verticalScroll(scrollState)) {
        newsItems.articles.forEach { news ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), onClick = {
                isShowDetail = news
            }) {
                Column(modifier = Modifier.padding(8.dp)) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(news.urlToImage)
                            .crossfade(true).build(),
                        error = painterResource(R.drawable.ic_broken_image),
                        placeholder = painterResource(R.drawable.loading_img),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(news.title)
                    Text(news.publishedAt)
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
