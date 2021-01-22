package com.appscrip.triviaapp.repository.local.repositories

import android.app.Application
import android.util.Log
import androidx.lifecycle.liveData
import com.appscrip.triviaapp.app_constants.Resource
import com.appscrip.triviaapp.repository.local.AppDatabase
import com.appscrip.triviaapp.repository.local.dao.QuizDao
import com.appscrip.triviaapp.repository.local.entities.QuizDetailEntity
import kotlinx.coroutines.Dispatchers

class QuizDataRepository(application: Application) {

    private var dao: QuizDao?

    init {
        val db = AppDatabase.getInstance(application)
        dao = db?.quizDao()
    }

    fun readQuizDetails() = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = dao?.getAllQuizDetails()
                )
            )
        } catch (exception: Exception) {
            Log.e("", exception.toString())
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun insertQuizDetails(products: ArrayList<QuizDetailEntity>) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = dao?.insertAllQuiz(products)
                )
            )
        } catch (exception: Exception) {
            Log.e("", exception.toString())
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun deleteAll() = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = dao?.deleteAll()
                )
            )
        } catch (exception: Exception) {
            Log.e("", exception.toString())
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}