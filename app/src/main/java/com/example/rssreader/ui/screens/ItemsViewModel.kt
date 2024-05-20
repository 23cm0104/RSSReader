package com.example.rssreader.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.rssreader.RSSReaderApplication
import com.example.rssreader.data.NewsRepository
import com.example.rssreader.model.NewsItems
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed interface ItemsUiState {
    data class Success(val news: NewsItems) : ItemsUiState
    data object Loading : ItemsUiState
    data object Error : ItemsUiState
}

class ItemsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    var itemsUiState: ItemsUiState by mutableStateOf(ItemsUiState.Loading)
        private set

    init {
        getItems(0)
    }

    fun getItems(index: Int) {
        viewModelScope.launch {
            itemsUiState = try {
                val items = newsRepository.getNewsItems(index)
                ItemsUiState.Success(
                    items
                )
            } catch (e: IOException) {
                ItemsUiState.Error
            } catch (e: HttpException) {
                ItemsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RSSReaderApplication)
                val newsRepository = application.container.newsRepository
                ItemsViewModel(newsRepository)
            }
        }
    }
}
