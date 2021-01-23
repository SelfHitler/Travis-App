package com.appscrip.triviaapp.views.adapters

import androidx.recyclerview.widget.RecyclerView
import com.appscrip.triviaapp.dto.QuizDetail
import com.appscrip.triviaapp.databinding.QuestionsAnswerViewBinding

class QaViewHolder(private val viewBinding: QuestionsAnswerViewBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(index: Int, quizDetail: QuizDetail) {
        viewBinding.apply {
            position = index
            quizDetails = quizDetail

        }
    }

}
