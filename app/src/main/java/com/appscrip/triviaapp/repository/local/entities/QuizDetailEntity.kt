package com.appscrip.triviaapp.repository.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appscrip.triviaapp.dto.QuizDetail
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "quiz_details")
data class QuizDetailEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int?,

    var createDate: Date,

    val quizDetails: ArrayList<QuizDetail>?,

    var nameOfThePlayer: String

)
