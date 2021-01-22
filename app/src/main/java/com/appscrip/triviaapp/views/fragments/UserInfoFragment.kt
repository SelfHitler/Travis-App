package com.appscrip.triviaapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.appscrip.triviaapp.R
import com.appscrip.triviaapp.databinding.FragmentUserInfoBinding
import com.appscrip.triviaapp.view_models.QuizViewModel
import org.koin.android.ext.android.inject

class UserInfoFragment() : Fragment() {

    private val viewModel by inject<QuizViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentUserInfoBinding: FragmentUserInfoBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_info, container, false)
        fragmentUserInfoBinding.viewModel = viewModel

        fragmentUserInfoBinding.lifecycleOwner = this

        return fragmentUserInfoBinding.root
    }

}