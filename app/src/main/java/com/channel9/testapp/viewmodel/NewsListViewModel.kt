package com.channel9.testapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.channel9.testapp.model.News
import com.channel9.testapp.service.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    private val _newsList: MutableLiveData<State> = MutableLiveData()
    val newsList: LiveData<State> = _newsList

    init {
        getNewsList()
    }

    private fun getNewsList() {
        _newsList.value = State.Loading

        viewModelScope.launch {
            repository.getNewsList().let { result ->
                _newsList.value = if (result.isSuccess) {
                    result.getOrNull()?.let { list ->
                        if (list.isEmpty()) {
                            State.Empty
                        } else {
                            State.Success(list.sortedBy { it.timeStamp })
                        }
                    } ?: run {
                        State.Empty
                    }
                } else {
                    State.Failure(result.exceptionOrNull())
                }
            }
        }
    }
}

/**
 * UI States during data fetch from API
 */
sealed class State {
    object Loading : State()
    data class Success(val data: List<News>) : State()
    object Empty : State()
    data class Failure(val throwable: Throwable?) : State()
}