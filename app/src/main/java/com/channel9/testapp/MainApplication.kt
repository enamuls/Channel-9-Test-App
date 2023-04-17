package com.channel9.testapp

import android.app.Application
import com.channel9.testapp.service.NewsApi
import com.channel9.testapp.service.NewsService
import com.channel9.testapp.service.NewsServiceImpl
import com.channel9.testapp.service.repository.NewsRepository
import com.channel9.testapp.service.repository.NewsRepositoryImpl
import com.channel9.testapp.viewmodel.NewsListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}

val appModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://bruce-v2-mob.fairfaxmedia.com.au/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<NewsApi> { get<Retrofit>().create(NewsApi::class.java) }

    single<NewsService> { NewsServiceImpl(get()) }

    single<NewsRepository> { NewsRepositoryImpl(get()) }

    single { NewsListViewModel(get()) }
}