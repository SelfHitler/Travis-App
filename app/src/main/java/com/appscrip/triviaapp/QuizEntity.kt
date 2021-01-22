package com.appscrip.triviaapp

class QuizDetail {

    var question: String? = ""

    var hint: String? = ""

    var options: ArrayList<String> = ArrayList()

    var selectionType: QuestionType? = QuestionType.SINGLE_SELECTION

    var selectedOptions: ArrayList<String> = ArrayList()

}

enum class QuestionType {
    SINGLE_SELECTION, MULTIPLE_SELECTION
}