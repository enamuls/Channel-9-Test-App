package com.channel9.testapp.model

data class News(
    val id: Long,
    val url: String,
    val headline: String,
    val theAbstract: String,
    val byLine: String,
    val thumbnailUrl: String?,
    val timeStamp: Long
) {
    val isThumbnailUrlAvailable: Boolean = thumbnailUrl.isNullOrEmpty().not()
}
