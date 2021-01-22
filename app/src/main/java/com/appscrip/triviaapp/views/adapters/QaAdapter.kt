package com.appscrip.triviaapp.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.appscrip.triviaapp.QuizDetail
import com.appscrip.triviaapp.R
import com.appscrip.triviaapp.databinding.QuestionsAnswerViewBinding

class QaAdapter(
    private val questionsAnswerList: ArrayList<QuizDetail>
) :
    RecyclerView.Adapter<QaViewHolder>() {

    lateinit var questionsAnswerViewBinding: QuestionsAnswerViewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QaViewHolder {
        questionsAnswerViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.questions_answer_view,
            parent,
            false
        )

        return QaViewHolder(
            questionsAnswerViewBinding
        )
    }

    override fun onBindViewHolder(holder: QaViewHolder, position: Int) {
        holder.bind(position, questionsAnswerList[position])
    }

    override fun getItemCount(): Int {
        return questionsAnswerList.size
    }

}
