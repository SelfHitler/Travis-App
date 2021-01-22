package com.appscrip.triviaapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.appscrip.triviaapp.R
import com.appscrip.triviaapp.databinding.FragmentSummaryDialogBinding
import com.appscrip.triviaapp.view_models.QuizViewModel
import org.koin.android.ext.android.inject

class SummaryDialogFragment() : DialogFragment() {

    private val viewModel by inject<QuizViewModel>()

    lateinit var summaryDialogBinding: FragmentSummaryDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isCancelable = false

        summaryDialogBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_summary_dialog, container, false)
        summaryDialogBinding.viewModel = viewModel
        summaryDialogBinding.lifecycleOwner = this

        return summaryDialogBinding.root
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

}