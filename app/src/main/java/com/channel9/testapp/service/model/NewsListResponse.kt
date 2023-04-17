package com.channel9.testapp.service.model

data class NewsListResponse(
    val assets: List<NewsResponse>
)

data class NewsResponse(
    val id: Long,
    val url: String,
    val headline: String,
    val theAbstract: String,
    val byLine: String,
    val relatedImages: List<RelatedImageResponse>,
    val timeStamp: Long
)

data class RelatedImageResponse(
    val url: String,
    val type: String
)