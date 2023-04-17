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

    private val newsList = listOf(
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
    private val throwable = Throwable("some error")
    private val mockRepositoryWithData = object : NewsRepository {
        override suspend fun getNewsList(): Result<List<News>> {
            return Result.success(newsList)
        }
    }
    private val mockRepositoryWithNoData = object : NewsRepository {
        override suspend fun getNewsList(): Result<List<News>> {
            return Result.success(emptyList())
        }
    }
    private val mockRepositoryWithFailure = object : NewsRepository {
        override suspend fun getNewsList(): Result<List<News>> {
            return Result.failure(throwable)
        }
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
        sut = NewsListViewModel(mockRepositoryWithData)

        assertEquals(State.Success(newsList), sut.newsList.first())
    }

    @Test
    fun test_getNewsList_isSuccessfulWithNoNews() = runTest {
        sut = NewsListViewModel(mockRepositoryWithNoData)

        assertEquals(State.Empty, sut.newsList.first())
    }

    @Test
    fun test_getNewsList_isFailure() = runTest {
        sut = NewsListViewModel(mockRepositoryWithFailure)

        assertEquals(State.Failure(throwable), sut.newsList.first())
    }
}