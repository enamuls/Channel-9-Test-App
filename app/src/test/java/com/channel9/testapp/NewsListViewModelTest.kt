@file:OptIn(ExperimentalCoroutinesApi::class)

package com.channel9.testapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.channel9.testapp.model.News
import com.channel9.testapp.service.repository.NewsRepository
import com.channel9.testapp.viewmodel.NewsListViewModel
import com.channel9.testapp.viewmodel.State
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NewsListViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var sut: NewsListViewModel

    private val mockNews1 = News(
        id = 1234567,
        url = "test/url/1",
        headline = "headline1",
        theAbstract = "theAbstract1",
        byLine = "byLine1",
        thumbnailUrl = null,
        timeStamp = 123456789
    )
    private val mockNews2 = News(
        id = 7654321,
        url = "test/url/2",
        headline = "headline2",
        theAbstract = "theAbstract2",
        byLine = "byLine2",
        thumbnailUrl = "some/thumbnail/url/2",
        timeStamp = 987654321
    )
    private val mockNewsList1 = listOf(mockNews1)
    private val mockNewsList2 = listOf(mockNews1, mockNews2)
    private val mockThrowable = Throwable("some error")
    private val mockRepositoryWithOneNews = object : NewsRepository {
        override suspend fun getNewsList(): Result<List<News>> = Result.success(mockNewsList1)
    }
    private val mockRepositoryWithTwoNews = object : NewsRepository {
        override suspend fun getNewsList(): Result<List<News>> = Result.success(mockNewsList2)
    }
    private val mockRepositoryWithNoNews = object : NewsRepository {
        override suspend fun getNewsList(): Result<List<News>> = Result.success(emptyList())
    }
    private val mockRepositoryWithFailure = object : NewsRepository {
        override suspend fun getNewsList(): Result<List<News>> = Result.failure(mockThrowable)
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_getNewsList_isSuccessfulWithOneNews() = runTest {
        sut = NewsListViewModel(mockRepositoryWithOneNews)

        assertEquals(State.Success(mockNewsList1), sut.newsList.first())
    }

    @Test
    fun test_getNewsList_isSuccessfulWithNoNews() = runTest {
        sut = NewsListViewModel(mockRepositoryWithNoNews)

        assertEquals(State.Empty, sut.newsList.first())
    }

    @Test
    fun test_getNewsList_isFailure() = runTest {
        sut = NewsListViewModel(mockRepositoryWithFailure)

        assertEquals(State.Failure(mockThrowable), sut.newsList.first())
    }

    @Test
    fun test_getNewsList_hasTwoNews_sortedSuccessfully() = runTest {
        sut = NewsListViewModel(mockRepositoryWithTwoNews)

        assertEquals(State.Success(listOf(mockNews2, mockNews1)), sut.newsList.first())
    }
}