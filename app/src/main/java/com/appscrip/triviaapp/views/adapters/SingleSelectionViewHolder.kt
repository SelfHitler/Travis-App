package com.appscrip.triviaapp.views.adapters

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.appscrip.triviaapp.dto.QuizDetail
import com.appscrip.triviaapp.databinding.QuizSingleSelectionViewBinding
import com.appscrip.triviaapp.view_models.QuizViewModel

class SelectionViewHolder(private val binding: QuizSingleSelectionViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        position: Int,
        viewModel: QuizViewModel,
        lifeCycleOwner: LifecycleOwner,
        quizDetail: QuizDetail
    ) {
        binding.position = position
        binding.viewModel = viewModel
        binding.lifecycleOwner = lifeCycleOwner
        binding.quizDetails = quizDetail
    }

}
