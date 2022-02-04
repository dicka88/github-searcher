package com.dickaismaji.github.ui.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsUserGithubDetailPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
  var username: String = ""

  override fun getItemCount(): Int  = 2

  override fun createFragment(position: Int): Fragment {
    var fragment: Fragment? = null
    val mBundle = Bundle()

    when(position) {
      0 ->  {
        val userGithubFollowingFragment = UserGithubFollowingFragment()
        mBundle.putString(UserGithubFollowingFragment.EXTRA_USERNAME, username)
        userGithubFollowingFragment.arguments = mBundle
        fragment = userGithubFollowingFragment
      }
      1 -> {
        val userGithubFollowerFragment = UserGithubFollowerFragment()
        mBundle.putString(UserGithubFollowerFragment.EXTRA_USERNAME, username)
        userGithubFollowerFragment.arguments = mBundle
        fragment = userGithubFollowerFragment
      }
    }

    return fragment as Fragment
  }
}