package com.batro

import android.app.Application
import com.airbnb.mvrx.Mavericks
import com.batro.di.baseModule
import com.batro.domain.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this)
        startKoin {
            androidContext(this@App)
            modules(baseModule, domainModule)
        }
        if (BuildConfig.DEBUG) {
            System.setProperty(
                kotlinx.coroutines.DEBUG_PROPERTY_NAME,
                kotlinx.coroutines.DEBUG_PROPERTY_VALUE_ON
            )
        }
    }
}