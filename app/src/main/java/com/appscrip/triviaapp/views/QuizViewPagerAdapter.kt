package com.appscrip.triviaapp.views

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.appscrip.triviaapp.views.fragments.QuizPage
import com.appscrip.triviaapp.views.fragments.UserInfoFragment

class QuizViewPagerAdapter(fa: AppCompatActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                UserInfoFragment()
            }
            else -> {
                QuizPage()
            }
        }
    }

}