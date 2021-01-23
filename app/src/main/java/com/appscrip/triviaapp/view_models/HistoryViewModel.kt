package com.appscrip.triviaapp.view_models

import android.annotation.SuppressLint
import android.app.Application
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appscrip.triviaapp.models.CommonModel
import com.appscrip.triviaapp.repository.local.entities.QuizDetailEntity
import com.appscrip.triviaapp.repository.local.repositories.QuizDataRepository
import com.appscrip.triviaapp.views.adapters.HistoryAdapter
import com.appscrip.triviaapp.views.adapters.SelectionAdapter
import org.koin.java.KoinJavaComponent.inject

class HistoryViewModel(application: Application, val repository: QuizDataRepository) :
    AndroidViewModel(application) {

    val listOfQuiz = MutableLiveData<List<QuizDetailEntity>>()

    val commonModel by inject(CommonModel::class.java)

    companion object {
        @JvmStatic
        @BindingAdapter("setHistoryAdapter", requireAll = false)
        fun setHistoryAdapter(
            recyclerView: RecyclerView,
            listOfQuizDetails: List<QuizDetailEntity>?
        ) {
            listOfQuizDetails?.let {
                recyclerView.layoutManager =
                    LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false)

                recyclerView.adapter = HistoryAdapter(listOfQuizDetails)
            }
        }

        @SuppressLint("SetTextI18n")
        @JvmStatic
        @BindingAdapter("setGameNumber")
        fun setGameNumber(textView: TextView, position: Int) {
            textView.text = "${position + 1}"
        }
    }

}