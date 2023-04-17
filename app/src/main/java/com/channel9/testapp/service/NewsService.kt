package com.channel9.testapp.service

import com.channel9.testapp.model.News
import com.channel9.testapp.service.mapper.NewsMapper

class NewsService(
    private val newsApi: NewsApi
) {
    private val newsMapper = NewsMapper()

    suspend fun getNewsList(): Result<List<News>> {
        val response = newsApi.getNewsList()

        return try {
            response.body()?.let { body ->
                Result.success(newsMapper.mapResponseToNewsList(body))
            } ?: run {
                Result.success(emptyList())
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}