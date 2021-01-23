package com.appscrip.triviaapp.base

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/*
* entry Point of the application
* */
class MainApplication : Application() {

    override fun onCreate() {

        super.onCreate()

        startKoin {

            androidLogger()

            androidContext(this@MainApplication)

            koin.loadModules(AppModules().getModules())

            koin.createRootScope()

        }

    }
}