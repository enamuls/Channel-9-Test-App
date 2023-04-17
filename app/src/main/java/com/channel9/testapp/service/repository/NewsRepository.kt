package com.channel9.testapp.service.repository

import com.channel9.testapp.model.News
import com.channel9.testapp.service.NewsService

interface NewsRepository {
    suspend fun getNewsList(): Result<List<News>>
}

class NewsRepositoryImpl(
    private val service: NewsService
) : NewsRepository {
    override suspend fun getNewsList(): Result<List<News>> {
        return service.getNewsList()
    }
}