package com.channel9.testapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.channel9.testapp.model.News
import com.channel9.testapp.service.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    private val _newsList: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val newsList: StateFlow<State> = _newsList

    init {
        getNewsList()
    }

    private fun getNewsList() {
        viewModelScope.launch {
            _newsList.value = State.Loading

            repository.getNewsList().let { result ->
                _newsList.value = if (result.isSuccess) {
                    val newsList = result.getOrNull()

                    if (newsList.isNullOrEmpty()) {
                        State.Empty
                    } else {
                        State.Success(newsList.sortByTimeStamp())
                    }
                } else {
                    State.Failure(result.exceptionOrNull())
                }
            }
        }
    }

    /**
     * Sort the news by timeStamp in descending order so the latest is on the top
     * @return list of [News]
     */
    private fun List<News>.sortByTimeStamp() =
        sortedByDescending { it.timeStamp }
}

/**
 * UI States during data fetching from API
 */
sealed class State {
    object Loading : State()
    data class Success(val data: List<News>) : State()
    object Empty : State()
    data class Failure(val throwable: Throwable?) : State()
}