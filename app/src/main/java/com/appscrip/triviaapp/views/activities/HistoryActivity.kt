package com.appscrip.triviaapp.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.appscrip.triviaapp.R
import com.appscrip.triviaapp.app_constants.Status
import com.appscrip.triviaapp.databinding.ActivityHistoryBinding
import com.appscrip.triviaapp.view_models.HistoryViewModel
import org.koin.android.ext.android.inject

class HistoryActivity : AppCompatActivity() {

    lateinit var activityHistoryBinding: ActivityHistoryBinding

    val viewModel by inject<HistoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHistoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_history)
        activityHistoryBinding.viewModel = viewModel
        activityHistoryBinding.lifecycleOwner = this

        viewModel.repository.readQuizDetails().observe(this, { it ->
            it.status.let { status ->
                when (status) {
                    Status.SUCCESS -> {
                        viewModel.listOfQuiz.postValue(it.data)
                        viewModel.commonModel.showToastTextView("History Received Successfully")
                    }
                    Status.ERROR -> {
                        viewModel.commonModel.showToastTextView("Unable To Fetch History")
                    }
                    Status.LOADING -> {
                        viewModel.commonModel.showToastTextView("Loading History")
                    }
                }
            }
        })

    }

    fun startQuiz(view: View) {
        viewModel.commonModel.showToastTextView("Please Wait")
        finish()
    }
}