package com.appscrip.triviaapp.views.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.appscrip.triviaapp.R
import com.appscrip.triviaapp.ZoomOutPageTransformer
import com.appscrip.triviaapp.app_constants.ActionListenerKeys
import com.appscrip.triviaapp.app_constants.Status
import com.appscrip.triviaapp.databinding.ActivityMainBinding
import com.appscrip.triviaapp.repository.local.entities.QuizDetailEntity
import com.appscrip.triviaapp.view_models.QuizViewModel
import com.appscrip.triviaapp.views.adapters.QuizViewPagerAdapter
import com.appscrip.triviaapp.views.fragments.SummaryDialogFragment
import org.koin.android.ext.android.inject
import java.util.*
import kotlin.collections.ArrayList

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
                    ActionListenerKeys.SHOW_SUMMARY_DIALOG.name -> {
                        saveQuizDetailsToDatabase()
                        showDialog()
                    }
                    ActionListenerKeys.DISMISS_SUMMARY_DIALOG.name -> {
                        dismissSummaryFragment()
                    }
                    else -> {

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

    private val dialogFragment = SummaryDialogFragment()

    private fun showDialog() {
        dialogFragment.show(supportFragmentManager, "summary_fragment")
    }

    private fun saveQuizDetailsToDatabase() {
        val quizDetailsEntities = ArrayList<QuizDetailEntity>().apply {
            add(
                QuizDetailEntity(
                    createDate = Date(),
                    quizDetails = viewModel.quizDetails.value,
                    nameOfThePlayer = viewModel.name.value ?: "",
                    id = null
                )
            )
        }

        viewModel.repository.insertQuizDetails(
            quizDetailsEntities
        ).observe(this, { it ->
            it.status.let {
                Log.e("Status -> ", "" + it.name)
            }
        })

    }

    private fun dismissSummaryFragment() {
        dialogFragment.dismiss()
    }

}