package com.channel9.testapp.model

/**
 * Data class that represents a News item
 */
data class News(
    val id: Long,
    val url: String,
    val headline: String,
    val theAbstract: String,
    val byLine: String,
    val thumbnailUrl: String?,
    val timeStamp: Long
) {

    /**
     * Checks if [thumbnailUrl] is available (not null or empty)
     */
    val isThumbnailUrlAvailable: Boolean = thumbnailUrl.isNullOrEmpty().not()
}
