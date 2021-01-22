package com.appscrip.triviaapp.view_models

import android.app.Application
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appscrip.triviaapp.QuestionType
import com.appscrip.triviaapp.QuizDetail
import com.appscrip.triviaapp.R
import com.appscrip.triviaapp.models.CommonModel
import com.appscrip.triviaapp.views.adapters.SelectionAdapter
import com.google.android.material.button.MaterialButton
import org.koin.java.KoinJavaComponent.inject

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    val commonModel by inject(CommonModel::class.java)

    val name = MutableLiveData<String>()
    val pageNumber = MutableLiveData<Int>().apply { value = 0 }
    val allowToOpenNextPage = MutableLiveData<Boolean>().apply { value = true }
    val allowToOpenPreviousPage = MutableLiveData<Boolean>().apply { value = false }

    var quizDetails = MutableLiveData<ArrayList<QuizDetail>>()

    init {

        resetQuizData()

    }

    private fun resetQuizData() {
        val questionDetails = ArrayList<QuizDetail>()
        questionDetails.apply {

            questionDetails.add(QuizDetail().apply {
                this.selectionType = QuestionType.SINGLE_SELECTION
                this.question = "who is the best cricketer in the world?"
                this.selectedOptions = ArrayList()
                this.hint = "Select any one"

                this.options.apply {
                    add("Sachin Tendulkar")
                    add("Virat Kolli")
                    add("Adam Gilchirst")
                    add("Jacques Kallis")
                }
            })

            this.add(QuizDetail().apply {
                this.selectionType = QuestionType.MULTIPLE_SELECTION
                this.question = "What are the colors in the Indian national flag?"
                this.selectedOptions = ArrayList()
                this.hint = "Select more than one"
                this.options.apply {
                    add("White")
                    add("Yellow")
                    add("Orange")
                    add("Green")
                }
            })
        }

        quizDetails.value = questionDetails
    }

    fun slideToNextPage() {
        validatePageDetails(false)
    }

    private fun validatePageDetails(goBack: Boolean) {
        when (pageNumber.value) {
            1 -> {
                //validate Single selection
                //if validation true then
                if (quizDetails.value?.get(0)?.selectedOptions.isNullOrEmpty()) {
                    commonModel.showToastTextView("Please Select Your Answer")
                    return
                }

            }
            2 -> {
                // no need to validate.
            }
            else -> {
                //check user Entered Name or not
                if (name.value.isNullOrEmpty()) {
                    commonModel.showToastTextView("Enter Your Name ")
                    return
                }

            }
        }

        if (goBack) {
            pageNumber.value = pageNumber.value?.minus(1)
        } else {
            pageNumber.value = pageNumber.value?.plus(1)
        }

        when (pageNumber.value) {
            0 -> {
                allowToOpenNextPage.postValue(true)
                allowToOpenPreviousPage.postValue(false)
            }
            1 -> {
                allowToOpenNextPage.postValue(true)
                allowToOpenPreviousPage.postValue(true)
            }
            2 -> {
                allowToOpenNextPage.postValue(false)
                allowToOpenPreviousPage.postValue(true)
            }
        }

    }

    fun submitQuiz() {
        if (quizDetails.value?.get(1)?.selectedOptions.isNullOrEmpty()) {
            commonModel.showToastTextView("Please Select Your Answer")
            return
        }

        // save Data in DB

    }

    fun slideToPreviousPage() {
        validatePageDetails(true)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("allowToSubmit")
        fun allowToSubmit(button: MaterialButton, pageNumber: Int) {
            when (pageNumber) {
                2 -> {
                    button.visibility = View.VISIBLE
                }
                else -> {
                    button.visibility = View.GONE
                }
            }
        }

        @JvmStatic
        @BindingAdapter("pageNumber")
        fun pageNumber(imageView: ImageView, pageNumber: Int) {
            if (pageNumber == 1) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        imageView.context,
                        R.drawable.ic_baseline_looks_one_24
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        imageView.context,
                        R.drawable.ic_baseline_looks_two_24
                    )
                )
            }
        }

        @JvmStatic
        @BindingAdapter("setSelectionAdapter", "lifecycle")
        fun setSelectionAdapter(
            recyclerView: RecyclerView,
            quizDetail: QuizDetail,
            lifecycle: LifecycleOwner
        ) {
            recyclerView.layoutManager =
                LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false)

            recyclerView.adapter = SelectionAdapter(lifecycle, quizDetail)
        }
    }
}