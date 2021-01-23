package com.appscrip.triviaapp.view_models

import android.app.Application
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appscrip.triviaapp.dto.QuestionType
import com.appscrip.triviaapp.dto.QuizDetail
import com.appscrip.triviaapp.R
import com.appscrip.triviaapp.app_constants.ActionListenerKeys
import com.appscrip.triviaapp.models.CommonModel
import com.appscrip.triviaapp.repository.local.repositories.QuizDataRepository
import com.appscrip.triviaapp.views.adapters.QaAdapter
import com.appscrip.triviaapp.views.adapters.SelectionAdapter
import com.google.android.material.button.MaterialButton
import org.koin.java.KoinJavaComponent.inject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class QuizViewModel(application: Application, val repository: QuizDataRepository) :
    AndroidViewModel(application) {

    val commonModel by inject(CommonModel::class.java)

    val name = MutableLiveData<String>()
    val pageNumber = MutableLiveData<Int>().apply { value = 0 }
    val allowToOpenNextPage = MutableLiveData<Boolean>().apply { value = true }
    val allowToOpenPreviousPage = MutableLiveData<Boolean>().apply { value = false }

    var quizDetails = MutableLiveData<ArrayList<QuizDetail>>()

    init {

        resetQuizData()

    }

    /*
    * To Reset Quiz Data to default
    * */
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
        pageNumber.postValue(0)

        name.postValue("")

        allowToOpenNextPage.postValue(true)

        allowToOpenPreviousPage.postValue(false)

        quizDetails.value = questionDetails
    }

    /*
    * onClick of forward button this functionality will call
    * */
    fun slideToNextPage() {
        validatePageDetails(false)
    }

    /*
    * onClick of show history button this functionality will call.
    * After Reset the Quiz Details, It will redirect to the Dialog Fragment
    * */
    fun showHistory() {
        resetQuizData()
        commonModel.actionListener.postValue(ActionListenerKeys.SHOW_HISTORY.name)
    }

    fun startQuizAgain() {
        commonModel.actionListener.postValue(ActionListenerKeys.DISMISS_SUMMARY_DIALOG.name)
        resetQuizData()
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

        commonModel.actionListener.postValue(ActionListenerKeys.SHOW_SUMMARY_DIALOG.name)

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
        @BindingAdapter("setNameText")
        fun setNameText(textView: TextView, name: String) {
            textView.text = "Hello $name"
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

        @JvmStatic
        @BindingAdapter("quizData")
        fun answerAdapter(
            recyclerView: RecyclerView,
            quizData: ArrayList<QuizDetail>
        ) {
            recyclerView.layoutManager =
                LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false)

            recyclerView.adapter = QaAdapter(quizData)
        }

        @JvmStatic
        @BindingAdapter("setSelectedAnswer")
        fun setSelectedAnswer(textView: TextView, selections: ArrayList<String>) {
            textView.text = selections.toString().replace("[", "").replace("]", "")
        }

        @JvmStatic
        @BindingAdapter("setTime", requireAll = false)
        fun setTime(textView: TextView, date: Date?) {
            val pattern = "dd-MM-yyyy hh:mm aaa"
            val simpleDateFormat = SimpleDateFormat(pattern)
            val dateString: String = simpleDateFormat.format(date ?: Date())
            textView.text = dateString
        }
    }
}
