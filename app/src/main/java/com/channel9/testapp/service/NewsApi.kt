package com.channel9.testapp.service

import com.channel9.testapp.service.model.NewsListResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * API Interface for network requests
 */
interface NewsApi {
    @GET("1/coding_test/13ZZQX/full/")
    suspend fun getNewsList(): Response<NewsListResponse>
}