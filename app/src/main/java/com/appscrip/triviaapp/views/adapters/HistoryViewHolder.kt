package com.appscrip.triviaapp.views.adapters

import androidx.recyclerview.widget.RecyclerView
import com.appscrip.triviaapp.databinding.HistoryViewBinding
import com.appscrip.triviaapp.repository.local.entities.QuizDetailEntity

class HistoryViewHolder(private val viewBinding: HistoryViewBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(quizDetailEntity: QuizDetailEntity,position:Int) {
        viewBinding.data = quizDetailEntity
        viewBinding.position = position
    }
}
