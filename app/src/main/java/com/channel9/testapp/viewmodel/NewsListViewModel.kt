package com.channel9.testapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.channel9.testapp.model.News
import com.channel9.testapp.service.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Responsible for NewsListFragment. Fetches a list of news then updates it's listeners
 */
class NewsListViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    /**
     * Local flow that can be modified. Initial state is [State.Loading]
     */
    private val _newsList: MutableStateFlow<State> = MutableStateFlow(State.Loading)

    /**
     * Public flow that can be listened to
     */
    val newsList: StateFlow<State> = _newsList

    /**
     * On viewModel init fetch list of News
     */
    init {
        getNewsList()
    }

    /**
     * Fetch a list of news from the API then update UI state accordingly.
     * Initially
     *  [State.Loading] is set
     * Based on then response
     *  [State.Empty] is set if no news returned
     *  [State.Success] is set with a list of news
     *  [State.Failure] is set if any error occurred during network request
     */
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
 * UI States for NewsListFragment
 */
sealed class State {
    object Loading : State()
    data class Success(val data: List<News>) : State()
    object Empty : State()
    data class Failure(val throwable: Throwable?) : State()
}