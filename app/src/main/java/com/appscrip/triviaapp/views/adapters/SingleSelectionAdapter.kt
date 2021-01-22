package com.appscrip.triviaapp.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.appscrip.triviaapp.QuestionType
import com.appscrip.triviaapp.QuizDetail
import com.appscrip.triviaapp.R
import com.appscrip.triviaapp.databinding.QuizSingleSelectionViewBinding
import com.appscrip.triviaapp.view_models.QuizViewModel
import kotlinx.android.synthetic.main.quiz_single_selection_view.view.*
import org.koin.java.KoinJavaComponent.inject

class SelectionAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val quizDetail: QuizDetail
) :
    RecyclerView.Adapter<SelectionViewHolder>() {

    private lateinit var quizSingleSelectionViewBinding: QuizSingleSelectionViewBinding
    private val viewModel by inject(QuizViewModel::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionViewHolder {
        quizSingleSelectionViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.quiz_single_selection_view,
            parent,
            false
        )

        return SelectionViewHolder(
            quizSingleSelectionViewBinding
        )
    }

    override fun getItemCount(): Int {
        return quizDetail.options.size
    }

    override fun onBindViewHolder(holder: SelectionViewHolder, position: Int) {
        holder.bind(position, viewModel, lifecycleOwner, quizDetail)

        holder.itemView.cardView.setOnClickListener {
            if (quizDetail.selectionType == QuestionType.MULTIPLE_SELECTION) {

                holder.itemView.cardView.toggle()

                if (!quizDetail.selectedOptions.contains(quizDetail.options[position])) {
                    quizDetail.selectedOptions.add(quizDetail.options[position])
                } else {
                    quizDetail.selectedOptions.remove(quizDetail.options[position])
                }

            } else {
                when {
                    quizDetail.selectedOptions.isNullOrEmpty() -> {
                        holder.itemView.cardView.toggle()
                        quizDetail.selectedOptions.add(quizDetail.options[position])
                    }
                    quizDetail.selectedOptions.contains(quizDetail.options[position]) -> {
                        holder.itemView.cardView.toggle()
                        quizDetail.selectedOptions.remove(quizDetail.options[position])
                    }
                    else -> {
                        viewModel.commonModel.showToastTextView("Only one Selection Allowed")
                    }
                }
            }

            viewModel.pageNumber.value?.let {
                viewModel.quizDetails.value?.get(it-1)?.apply {
                    this.selectedOptions = quizDetail.selectedOptions
                }
            }

        }

        holder.itemView.cardView.isChecked = quizDetail.selectedOptions.contains(quizDetail.options[position])

    }

}