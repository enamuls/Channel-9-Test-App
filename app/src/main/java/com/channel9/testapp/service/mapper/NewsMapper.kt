package com.channel9.testapp.service.mapper

import com.channel9.testapp.model.News
import com.channel9.testapp.service.model.NewsListResponse
import com.channel9.testapp.service.model.NewsRelatedImageResponse

class NewsMapper {

    /**
     * Maps [NewsListResponse] to a list of [News]
     * @param newsListResponse: [NewsListResponse]
     * @return a list of [News]
     */
    fun mapResponseToNewsList(newsListResponse: NewsListResponse): List<News> =
        newsListResponse.assets.map { newsResponse ->
            News(
                id = newsResponse.id,
                url = newsResponse.url,
                headline = newsResponse.headline,
                theAbstract = newsResponse.theAbstract,
                byLine = newsResponse.byLine,
                thumbnailUrl = newsResponse.relatedImages.getThumbnailUrl(),
                timeStamp = newsResponse.timeStamp
            )
        }

    /**
     * Return a small image that is of the type "thumbnail"
     * @return [NewsRelatedImageResponse.url] of the "thumbnail" if exists
     */
    private fun List<NewsRelatedImageResponse>.getThumbnailUrl(): String? =
        find { it.type == "thumbnail" }?.url
}