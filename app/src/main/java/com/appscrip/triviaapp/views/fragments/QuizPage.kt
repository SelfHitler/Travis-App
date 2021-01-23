package com.appscrip.triviaapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.appscrip.triviaapp.R
import com.appscrip.triviaapp.databinding.FragmentQuizPageBinding
import com.appscrip.triviaapp.view_models.QuizViewModel
import org.koin.android.ext.android.inject

class QuizPage() : Fragment() {

    lateinit var fragmentQuizPageBinding: FragmentQuizPageBinding

    private val viewModel by inject<QuizViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentQuizPageBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_page, container, false)
        fragmentQuizPageBinding.viewModel = viewModel

        fragmentQuizPageBinding.quizDetails = if (viewModel.pageNumber.value == 1) {
            viewModel.quizDetails.value?.get(0)
        } else {
            viewModel.quizDetails.value?.get(1)
        }

        fragmentQuizPageBinding.lifeCycle = this
        fragmentQuizPageBinding.lifecycleOwner = this

        viewModel.pageNumber.observe(this) {
            fragmentQuizPageBinding.quizDetails = if (it == 1) {
                viewModel.quizDetails.value?.get(0)
            } else {
                viewModel.quizDetails.value?.get(1)
            }
        }

        return fragmentQuizPageBinding.root
    }

}