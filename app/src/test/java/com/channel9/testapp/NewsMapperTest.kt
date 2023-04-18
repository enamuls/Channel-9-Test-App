package com.channel9.testapp

import com.channel9.testapp.model.News
import com.channel9.testapp.service.mapper.NewsMapper
import com.channel9.testapp.service.model.NewsListResponse
import com.channel9.testapp.service.model.NewsResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NewsMapperTest {

    private lateinit var sut: NewsMapper

    private val mockId: Long = 123456
    private val mockUrl = "some/url"
    private val mockHeadline = "some headline"
    private val mockAbstract = "some abstract"
    private val mockAuthor = "some author"
    private val mockTimeStamp: Long = 123456789
    private val mockResponse1 = NewsListResponse(
        assets = listOf(
            NewsResponse(
                id = mockId,
                url = mockUrl,
                headline = mockHeadline,
                theAbstract = mockAbstract,
                byLine = mockAuthor,
                relatedImages = emptyList(),
                timeStamp = mockTimeStamp
            )
        )
    )
    private val mockResponse2 = NewsListResponse(
        assets = emptyList()
    )
    private val mockNews1 = News(
        id = mockId,
        url = mockUrl,
        headline = mockHeadline,
        theAbstract = mockAbstract,
        byLine = mockAuthor,
        thumbnailUrl = null,
        timeStamp = mockTimeStamp
    )

    @Before
    fun setUp() {
        sut = NewsMapper()
    }

    /**
     * Tests mapper maps one [News] correctly
     */
    @Test
    fun test_mapResponseToNewsList_responseHasOneNews_returnsListOfOneNews() {
        val expected = listOf(mockNews1)
        val actual = sut.mapResponseToNewsList(mockResponse1)

        assertEquals(expected, actual)
    }

    /**
     * Tests mapper maps an empty list correctly
     */
    @Test
    fun test_mapResponseToNewsList_responseHasNoNews_returnsEmptyListOfNews() {
        val expected = emptyList<News>()
        val actual = sut.mapResponseToNewsList(mockResponse2)

        assertEquals(expected, actual)
    }
}