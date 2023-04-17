package com.channel9.testapp.service.mapper

import com.channel9.testapp.model.News
import com.channel9.testapp.service.model.NewsListResponse

class NewsMapper {

    fun mapResponseToNewsList(newsListResponse: NewsListResponse): List<News> {
        return newsListResponse.assets.map { newsResponse ->
            val thumbnailUrl = newsResponse.relatedImages.find { it.type == "thumbnail" }?.url

            News(
                id = newsResponse.id,
                url = newsResponse.url,
                headline = newsResponse.headline,
                theAbstract = newsResponse.theAbstract,
                byLine = newsResponse.byLine,
                thumbnailUrl = thumbnailUrl,
                timeStamp = newsResponse.timeStamp
            )
        }
    }
}