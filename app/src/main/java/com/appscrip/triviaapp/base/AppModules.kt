package com.appscrip.triviaapp.base

import com.appscrip.triviaapp.models.CommonModel
import com.appscrip.triviaapp.repository.local.repositories.QuizDataRepository
import com.appscrip.triviaapp.view_models.HistoryViewModel
import com.appscrip.triviaapp.view_models.QuizViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

/*
* Base Module of the Application. This modules are use in KOIN DI
* */
class AppModules {

    fun getModules(): List<Module> {
        return listOf(appModule, databaseModule)
    }

    private val appModule = module {

        single { CommonModel() }

        single { QuizViewModel(androidApplication(), get()) }

        single { HistoryViewModel(androidApplication(), get()) }

    }

    private val databaseModule = module {
        single { QuizDataRepository(androidApplication()) }
    }


}