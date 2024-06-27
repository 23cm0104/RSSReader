package com.example.rssreader.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rssreader.R
import com.example.rssreader.model.Item

@Composable
fun NewsDetail(newsItems: Item, onDismiss: () -> Unit) {
    val context = LocalContext.current
    AlertDialog(onDismissRequest = onDismiss, confirmButton = {
        Button(onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsItems.link))
            context.startActivity(intent)
        }) {
            Text(text = "webへ行く")
        }
    }, dismissButton = {
        Button(onClick = onDismiss) {
            Text(text = "戻る")
        }
    }, title = {
        Text(text = newsItems.title)
    }, text = {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(newsItems.urlToImage)
                    .crossfade(true).build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(160.dp)
            )
            newsItems.description?.let { Text(text = it) }
        }


    })
}