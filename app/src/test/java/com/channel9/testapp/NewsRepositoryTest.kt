@file:OptIn(ExperimentalCoroutinesApi::class)

package com.channel9.testapp

import com.channel9.testapp.model.News
import com.channel9.testapp.service.NewsService
import com.channel9.testapp.service.repository.NewsRepository
import com.channel9.testapp.service.repository.NewsRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NewsRepositoryTest {

    private lateinit var sut: NewsRepository

    private val mockNewsList = listOf(
        News(
            id = 1234567,
            url = "test/url",
            headline = "headline",
            theAbstract = "theAbstract",
            byLine = "byLine",
            thumbnailUrl = null,
            timeStamp = 123456789
        )
    )
    private val mockThrowable = Throwable("some error")
    private val mockServiceWithData = object : NewsService {
        override suspend fun getNewsList(): Result<List<News>> {
            return Result.success(mockNewsList)
        }
    }
    private val mockServiceWithNoData = object : NewsService {
        override suspend fun getNewsList(): Result<List<News>> {
            return Result.success(emptyList())
        }
    }
    private val mockServiceWithFailure = object : NewsService {
        override suspend fun getNewsList(): Result<List<News>> {
            return Result.failure(mockThrowable)
        }
    }

    /**
     * Tests [Result.success] returns correct list with one [News]
     */
    @Test
    fun getNewsList_hasOneNews_returnsListOfOneNews() = runTest {
        sut = NewsRepositoryImpl(mockServiceWithData)

        val expected = mockNewsList
        val actual = sut.getNewsList()

        assertEquals(expected, actual.getOrNull())
    }

    /**
     * Tests [Result.success] returns correct list with no [News]
     */
    @Test
    fun getNewsList_hasNoNews_returnsEmptyListOfNews() = runTest {
        sut = NewsRepositoryImpl(mockServiceWithNoData)

        val expected = emptyList<News>()
        val actual = sut.getNewsList()

        assertEquals(expected, actual.getOrNull())
    }

    /**
     * Tests [Result.failure] returns a [Throwable]
     */
    @Test
    fun getNewsList_hasFailedToGetNews_returnsThrowable() = runTest {
        sut = NewsRepositoryImpl(mockServiceWithFailure)

        val expected = mockThrowable
        val actual = sut.getNewsList()

        assertEquals(expected, actual.exceptionOrNull())
    }
}