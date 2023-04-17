package com.channel9.testapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit

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
    single {
        Retrofit.Builder()
            .baseUrl("https://bruce-v2-mob.fairfaxmedia.com.au/1/coding_test/13ZZQX/full")
            .build()
    }
}