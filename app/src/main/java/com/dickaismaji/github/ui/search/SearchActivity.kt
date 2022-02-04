package com.dickaismaji.github.ui.search

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dickaismaji.github.R
import com.dickaismaji.github.databinding.ActivitySearchBinding
import com.dickaismaji.github.helper.UserPreference
import com.dickaismaji.github.models.response.search.user.UserItem
import com.dickaismaji.github.ui.favorite.FavoriteActivity
import com.dickaismaji.github.ui.setting.SettingActivity
import com.dickaismaji.github.ui.user.ListUsersGithubAdapter
import com.dickaismaji.github.ui.user.UserGithubDetailActivity
import kotlinx.coroutines.*

class SearchActivity : AppCompatActivity() {
  private lateinit var binding: ActivitySearchBinding
  private val viewModel by viewModels<SearchViewModel>()

  private var searchText: String = ""

  private lateinit var mUserPreference: UserPreference

  private val mHandler = Handler(Looper.getMainLooper())
  private val mDebounce = Runnable {
    run {
      if(searchText.isNotEmpty()) {
        viewModel.getUserList(searchText)
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivitySearchBinding.inflate(layoutInflater)

    setContentView(binding.root)

    supportActionBar?.apply {
      title = getString(R.string.app_name)
    }

    mUserPreference = UserPreference(this)

    setThemeSetting()

    Glide.with(this)
      .load("https://avatars.githubusercontent.com/u/50340947?v=4&w=48")
      .circleCrop()
      .placeholder(ColorDrawable(Color.GRAY))
      .error(ColorDrawable(Color.RED))
      .into(binding.imgAvatar)

    binding.imgAvatar.setOnClickListener {
      val username = getString(R.string.github_username)
      startActivity(Intent(this@SearchActivity, UserGithubDetailActivity::class.java).apply {
        putExtra(UserGithubDetailActivity.EXTRA_USERNAME, username)
      })
    }

    binding.toolbarFavorite.setOnClickListener {
      startActivity(Intent(this, FavoriteActivity::class.java))
    }

    binding.toolbarSetting.setOnClickListener {
      startActivity(Intent(this, SettingActivity::class.java))
    }

    binding.searchBar.addTextChangedListener(object: TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

      override fun afterTextChanged(s: Editable?) {
        searchText = binding.searchBar.text.toString()
        mHandler.removeCallbacks(mDebounce)
        mHandler.postDelayed(mDebounce, 700)
      }
    })

    binding.searchBar.requestFocus()

    viewModel.isLoading.observe(this, { isVisible ->
      binding.progressBar.visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
    })

    viewModel.listUsers.observe(this, { users ->
      if(users.count() > 0) {
        setUserSearchData(users)
        binding.viewNotFound.visibility = View.INVISIBLE
        binding.rvUserList.visibility = View.VISIBLE
      } else {
        binding.viewNotFound.visibility = View.VISIBLE
        binding.rvUserList.visibility = View.INVISIBLE
      }
    })
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return super.onSupportNavigateUp()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.option_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_favorite -> startActivity(Intent(this, FavoriteActivity::class.java))
      R.id.menu_setting -> startActivity(Intent(this, SettingActivity::class.java))
    }

    return super.onOptionsItemSelected(item)
  }

  private fun setThemeSetting() {
    val isDarkMode = mUserPreference.isDarkMode()

    if (isDarkMode) {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    } else {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
  }

  private fun setUserSearchData(userSearch: List<UserItem>) {
    val listUserGithubAdapter = ListUsersGithubAdapter(userSearch)
    binding.rvUserList.apply {
      layoutManager = LinearLayoutManager(this@SearchActivity)
      adapter = listUserGithubAdapter
    }

    listUserGithubAdapter.setItemOnClickCallback(object: ListUsersGithubAdapter.OnItemClickCallback {
      override fun onItemClicked(data: UserItem) {
        val username = data.login
        startActivity(Intent(this@SearchActivity, UserGithubDetailActivity::class.java).apply {
          putExtra(UserGithubDetailActivity.EXTRA_USERNAME, username)
        })
      }
    })
  }
}