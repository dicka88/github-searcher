package com.dickaismaji.github.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dickaismaji.github.R
import com.dickaismaji.github.database.FavoriteUser
import com.dickaismaji.github.databinding.ActivityFavoriteBinding
import com.dickaismaji.github.helper.ViewModelFactory
import com.dickaismaji.github.ui.user.UserGithubDetailActivity

class FavoriteActivity : AppCompatActivity() {
  private lateinit var binding: ActivityFavoriteBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityFavoriteBinding.inflate(layoutInflater)

    setContentView(binding.root)

    supportActionBar?.apply {
      title = getString(R.string.favorite_user)
      setDisplayHomeAsUpEnabled(true)
    }

    val adapter = FavoriteUserListAdapter()

    adapter.setItemOnClickCallback(object: FavoriteUserListAdapter.OnItemClickCallback {
      override fun onItemClicked(data: FavoriteUser) {
        val username = data.login
        startActivity(Intent(this@FavoriteActivity, UserGithubDetailActivity::class.java).apply {
          putExtra(UserGithubDetailActivity.EXTRA_USERNAME, username)
        })
      }
    })

    binding.rvUserList.layoutManager = LinearLayoutManager(this)
    binding.rvUserList.adapter = adapter

    val favoriteViewModel = obtainViewModel(this)
    favoriteViewModel.getAllFavoriteUser().observe(this, { favoriteList ->
      if (favoriteList != null) {
        adapter.setListFavoriteUser(favoriteList)
      }
    })
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return super.onSupportNavigateUp()
  }

  private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
    val factory = ViewModelFactory.getInstance(activity.application)
    return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
  }
}