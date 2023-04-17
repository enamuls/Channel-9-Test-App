package com.channel9.testapp.service

import com.channel9.testapp.model.News
import com.channel9.testapp.service.mapper.NewsMapper

interface NewsService {

    /**
     * Returns a list of [News] fetched from the API
     * @return list of [News]
     */
    suspend fun getNewsList(): Result<List<News>>
}

class NewsServiceImpl(
    private val newsApi: NewsApi
) : NewsService {
    private val newsMapper = NewsMapper()

    override suspend fun getNewsList(): Result<List<News>> {
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