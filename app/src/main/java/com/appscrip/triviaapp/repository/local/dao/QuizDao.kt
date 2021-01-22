package com.appscrip.triviaapp.repository.local.dao

import androidx.room.*
import com.appscrip.triviaapp.repository.local.entities.QuizDetailEntity

@Dao
interface QuizDao {

    @Query("SELECT * FROM quiz_details")
    suspend fun getAllQuizDetails(): List<QuizDetailEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllQuiz(users: ArrayList<QuizDetailEntity>)

    @Delete
    suspend fun deleteQuizDetail(user: QuizDetailEntity)

    @Query("DELETE FROM quiz_details")
    suspend fun deleteAll()

}
