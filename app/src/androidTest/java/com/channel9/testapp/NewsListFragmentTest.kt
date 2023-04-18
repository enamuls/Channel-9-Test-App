package com.channel9.testapp

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.channel9.testapp.adapter.NewsListAdapter
import com.channel9.testapp.model.News
import com.channel9.testapp.service.repository.NewsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class NewsListFragmentTest : KoinTest {

    private lateinit var mockModule: Module
    private lateinit var mockRepository: NewsRepository
    private lateinit var job: Job

    private val mockHeadline1 = "Headline 1"
    private val mockHeadline2 = "Headline 2"
    private val mockAbstract1 = "Abstract 1"
    private val mockAbstract2 = "Abstract 2"
    private val mockAuthor1 = "Author 1"
    private val mockAuthor2 = "Author 2"
    private val mockNewsList = listOf(
        News(
            id = 1234567,
            url = "test/url/1",
            headline = mockHeadline1,
            theAbstract = mockAbstract1,
            byLine = mockAuthor1,
            thumbnailUrl = null,
            timeStamp = 123456789
        ),
        News(
            id = 1234567,
            url = "test/url/1",
            headline = mockHeadline2,
            theAbstract = mockAbstract2,
            byLine = mockAuthor2,
            thumbnailUrl = null,
            timeStamp = 123456789
        )
    )

    @Before
    fun setup() {
        mockRepository = Mockito.mock(NewsRepository::class.java)
        mockModule = module {
            single { mockRepository }
        }

        loadKoinModules(mockModule)
    }

    @After
    fun tearDown() {
        unloadKoinModules(mockModule)
        job.cancel()
    }

    /**
     * Tests correct list is visible on the screen with correct data
     */
    @Test
    fun test_newsListHasCorrectNewsItems() = runBlocking {
        job = launch {
            `when`(mockRepository.getNewsList()).thenReturn(Result.success(mockNewsList))

            launchActivity<MainActivity>()

            // Expect first item on the list to have correct values
            onView(withId(R.id.news_list_recycler_view)).check(matches(isDisplayed()))
            onView(withId(R.id.news_list_recycler_view)).check(
                matches(hasDescendant(withText(mockHeadline1)))
            )
            onView(withId(R.id.news_list_recycler_view)).check(
                matches(hasDescendant(withText(mockAbstract1)))
            )
            onView(withId(R.id.news_list_recycler_view)).check(
                matches(hasDescendant(withText(mockAuthor1)))
            )

            // Expect second item on the list to have correct values
            onView(withId(R.id.news_list_recycler_view)).check(
                matches(hasDescendant(withText(mockHeadline2)))
            )
            onView(withId(R.id.news_list_recycler_view)).check(
                matches(hasDescendant(withText(mockAbstract2)))
            )
            onView(withId(R.id.news_list_recycler_view)).check(
                matches(hasDescendant(withText(mockAuthor2)))
            )
        }
    }

    /**
     * Tests onClick behaviour on a [News] item navigates to FullNewsFragment
     */
    @Test
    fun test_onNewsItemClick_navigateToFullNews() = runBlocking {
        job = launch {
            `when`(mockRepository.getNewsList()).thenReturn(Result.success(mockNewsList))

            launchActivity<MainActivity>()

            // Click on the first item on the list
            onView(withId(R.id.news_list_recycler_view)).perform(
                actionOnItemAtPosition<NewsListAdapter.NewsListViewHolder>(0, click())
            )

            // Expect full news web view is open
            onView(withId(R.id.web_view)).check(matches(isDisplayed()))
        }
    }
}