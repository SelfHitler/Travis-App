package com.appscrip.triviaapp.repository.local

import androidx.room.TypeConverter
import com.appscrip.triviaapp.dto.QuizDetail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class QuizDataConverter {

    @TypeConverter
    fun saveQuizData(listOfString: ArrayList<QuizDetail?>?): String? {
        return Gson().toJson(listOfString)
    }

    @TypeConverter
    fun getQuizList(listOfString: String): ArrayList<QuizDetail>? {
        val typeToken = object : TypeToken<List<QuizDetail>>() {}.type
        return Gson().fromJson<ArrayList<QuizDetail>>(listOfString, typeToken)
    }
}