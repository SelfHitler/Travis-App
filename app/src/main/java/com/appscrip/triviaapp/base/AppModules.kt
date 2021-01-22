package com.appscrip.triviaapp.base

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.appscrip.triviaapp.app_constants.SharedPreferenceKeys
import com.appscrip.triviaapp.models.CommonModel
import com.appscrip.triviaapp.view_models.QuizViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

class AppModules {

    fun getModules(): List<Module> {
        return listOf(appModule)
    }

    private val appModule = module {

        single { CommonModel(get()) }

        single { QuizViewModel(androidApplication()) }

        single { getSharedPreferences(androidApplication()) }

        single { getSharedPreferencesEditor(get()) }

    }

    private fun getSharedPreferencesEditor(app: SharedPreferences): SharedPreferences.Editor =
        app.edit()

    private fun getSharedPreferences(app: Application): SharedPreferences =
        app.getSharedPreferences(SharedPreferenceKeys.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

}