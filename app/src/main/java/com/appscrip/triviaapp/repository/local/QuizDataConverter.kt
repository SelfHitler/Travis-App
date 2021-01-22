package com.appscrip.triviaapp.repository.local

import androidx.room.TypeConverter
import com.appscrip.triviaapp.QuizDetail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class QuizDataConverter {

    @TypeConverter
    fun saveAQuizData(listOfString: ArrayList<QuizDetail?>?): String? {
        return Gson().toJson(listOfString)
    }

    @TypeConverter
    fun getQuizList(listOfString: String?): ArrayList<QuizDetail?>? {
        return Gson().fromJson(
            listOfString,
            object : TypeToken<ArrayList<QuizDetail?>?>() {}.type
        )
    }
}