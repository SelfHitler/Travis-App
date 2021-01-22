package com.appscrip.triviaapp.models

import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.transition.TransitionManager

class CommonModel(private val sharedPreferences: SharedPreferences) {

    val actionListener = MutableLiveData<String>().apply { value = "" }

    val message = MutableLiveData<String>().apply { value = "NA" }

    val messageShown = MutableLiveData<Boolean>().apply { value = false }

    fun showToastTextView(message: String) {
        this.message.postValue(message)
        messageShown.postValue(true)
        Handler(Looper.getMainLooper()).postDelayed({
            messageShown.postValue(false)
        }, 2000)
    }

    fun saveString(key: String, value: String) {
        sharedPreferences.edit().apply {
            putString(key, value)
        }.also {
            it.apply()
        }
    }

    fun readString(key: String, defaultValue: String): String? {
        val result = sharedPreferences.getString(key, defaultValue)
        return result?.replace("null","")
    }

    companion object {

        @BindingAdapter("android:animatedVisibility")
        @JvmStatic
        fun setAnimatedVisibility(target: View, isVisible: Boolean) {

            TransitionManager.beginDelayedTransition(target.rootView as ViewGroup)
            target.visibility = if (isVisible) View.VISIBLE else View.GONE
        }

    }

}
