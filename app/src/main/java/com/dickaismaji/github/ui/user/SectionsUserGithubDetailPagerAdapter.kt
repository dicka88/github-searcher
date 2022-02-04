package com.dickaismaji.github.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dickaismaji.github.ui.user.UserGithubFollowerFragment
import com.dickaismaji.github.ui.user.UserGithubFollowingFragment

class SectionsUserGithubDetailPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
  var username: String = ""

  override fun getItemCount(): Int  = 2

  override fun createFragment(position: Int): Fragment {
    var fragment: Fragment? = null

    fragment = when(position) {
      0 -> UserGithubFollowingFragment(username)
      1 -> UserGithubFollowerFragment(username)
      else -> UserGithubFollowingFragment(username)
    }

    return fragment
  }
}