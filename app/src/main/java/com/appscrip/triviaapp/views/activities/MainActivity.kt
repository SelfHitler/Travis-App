package com.appscrip.triviaapp.views.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.appscrip.triviaapp.R
import com.appscrip.triviaapp.ZoomOutPageTransformer
import com.appscrip.triviaapp.app_constants.ActionListenerKeys
import com.appscrip.triviaapp.databinding.ActivityMainBinding
import com.appscrip.triviaapp.view_models.QuizViewModel
import com.appscrip.triviaapp.views.QuizViewPagerAdapter
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    private val viewModel by inject<QuizViewModel>()
    private val pagerAdapter = QuizViewPagerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.viewModel = viewModel
        activityMainBinding.lifecycleOwner = this

        activityMainBinding.pager.apply {
            adapter = pagerAdapter
            setPageTransformer(ZoomOutPageTransformer())
            isUserInputEnabled = false
            viewModel.pageNumber.value = currentItem
        }

        observeFields()

    }

    private fun observeFields() {
        viewModel.commonModel.actionListener.observe(this) {
            if (!it.isNullOrBlank()) {
                when (it) {
                    ActionListenerKeys.SLIDE_TO_NEXT_PAGE.name -> {
                        Log.e("NEXT SLIDE" + activityMainBinding.pager.currentItem, "")
                        //viewModel.validateNextSlide(activityMainBinding.pager.currentItem)
                    }
                    ActionListenerKeys.SLIDE_TO_PREVIOUS_PAGE.name -> {
                        Log.e("PREVIOUS SLIDE" + activityMainBinding.pager.currentItem, "")
                        //viewModel.validatePreviousSlide(activityMainBinding.pager.currentItem)
                    }
                }
                viewModel.commonModel.actionListener.value = ""
            }
        }

        viewModel.pageNumber.observe(this) {
            if (it < 2) {

                activityMainBinding.pager.apply {
                    postDelayed({ currentItem = it }, 100)
                }

            }
        }

    }

}