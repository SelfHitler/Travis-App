package com.appscrip.triviaapp.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.appscrip.triviaapp.R
import com.appscrip.triviaapp.databinding.HistoryViewBinding
import com.appscrip.triviaapp.repository.local.entities.QuizDetailEntity

class HistoryAdapter(private val listOfQuizDetails: List<QuizDetailEntity>) :
    RecyclerView.Adapter<HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        historyViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.history_view,
            parent,
            false
        )

        return HistoryViewHolder(
            historyViewBinding
        )
    }

    lateinit var historyViewBinding: HistoryViewBinding


    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listOfQuizDetails[position], position)
    }

    override fun getItemCount(): Int {
        return listOfQuizDetails.size
    }

}
