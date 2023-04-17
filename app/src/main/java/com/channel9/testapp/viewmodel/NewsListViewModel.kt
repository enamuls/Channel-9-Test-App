package com.channel9.testapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.channel9.testapp.model.News
import com.channel9.testapp.service.NewsService
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val service: NewsService
) : ViewModel() {

    private val _newsList: MutableLiveData<List<News>> = MutableLiveData()
    val newsList: LiveData<List<News>> = _newsList

    init {
        getNewsList()
    }

    private fun getNewsList() {
        viewModelScope.launch {
            service.getNewsList().let { result ->
                if (result.isSuccess) {
                    result.getOrNull()?.let { list ->
                        _newsList.value= list.sortedBy { it.timeStamp }
                    }
                }
            }
        }
    }
}