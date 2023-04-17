package com.channel9.testapp.service.model

/**
 * Data structure of the NewsList API response
 */
data class NewsListResponse(
    val assets: List<NewsResponse>
)

/**
 * Data structure of the News response
 */
data class NewsResponse(
    val id: Long,
    val url: String,
    val headline: String,
    val theAbstract: String,
    val byLine: String,
    val relatedImages: List<NewsRelatedImageResponse>,
    val timeStamp: Long
)

/**
 * Data structure of the RelatedImage response
 */
data class NewsRelatedImageResponse(
    val url: String,
    val type: String
)