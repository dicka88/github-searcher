package com.dickaismaji.github.ui.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dickaismaji.github.databinding.FragmentUserGithubFollowingBinding
import com.dickaismaji.github.models.response.search.user.UserItem

class UserGithubFollowingFragment: Fragment() {
  private var binding: FragmentUserGithubFollowingBinding? = null
  private val viewModel: UserGithubViewModel by viewModels()

  var username: String? = ""

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentUserGithubFollowingBinding.inflate(inflater, container, false)
    username = arguments?.getString(EXTRA_USERNAME)

    return binding!!.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel.getFollowings(username!!)

    viewModel.userFollowings.observe(this, { users ->
      setUserFollowings(users)
    })

    viewModel.isUserFollowingLoading.observe(this, { isLoading ->
      binding?.progressBar?.visibility = if(isLoading) View.VISIBLE else View.INVISIBLE
    })
  }

  private fun setUserFollowings(users: List<UserItem>) {
    if(users.isEmpty()) {
      binding?.notFoundView?.visibility = View.VISIBLE
    } else {
      binding?.notFoundView?.visibility = View.INVISIBLE
    }

    val listUserGithubAdapter = ListUsersGithubAdapter(users)
    binding?.rvUserList?.apply {
      layoutManager = LinearLayoutManager(activity)
      adapter = listUserGithubAdapter
    }

    listUserGithubAdapter.setItemOnClickCallback(object: ListUsersGithubAdapter.OnItemClickCallback {
      override fun onItemClicked(data: UserItem) {
        val username = data.login
        startActivity(Intent(activity, UserGithubDetailActivity::class.java).apply {
          putExtra(UserGithubDetailActivity.EXTRA_USERNAME, username)
        })
      }
    })
  }

  companion object {
    const val EXTRA_USERNAME = "username"
  }
}