package com.dickaismaji.github.ui.user

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dickaismaji.github.R
import com.dickaismaji.github.database.FavoriteUser
import com.dickaismaji.github.databinding.ActivityUserGithubDetailBinding
import com.dickaismaji.github.helper.DateHelper
import com.dickaismaji.github.helper.ViewModelFactory
import com.dickaismaji.github.models.response.user.UserResponse
import com.dickaismaji.github.ui.favorite.FavoriteViewModel
import com.google.android.material.tabs.TabLayoutMediator

class UserGithubDetailActivity : AppCompatActivity(), View.OnClickListener {
  private lateinit var binding: ActivityUserGithubDetailBinding
  private val viewModel by viewModels<UserGithubViewModel>()
  private lateinit var favoriteViewModel: FavoriteViewModel

  private lateinit var username: String

  private lateinit var user: UserResponse
  private var favoriteUser: FavoriteUser? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    username = intent.getStringExtra(EXTRA_USERNAME)!!

    binding = ActivityUserGithubDetailBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val sectionUserPagerAdapter = SectionsUserGithubDetailPagerAdapter(this)
    sectionUserPagerAdapter.username = username

    binding.viewPager.adapter = sectionUserPagerAdapter
    TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
      tab.text = resources.getString(TAB_TITLES[position])
    }.attach()

    supportActionBar?.title = username
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    favoriteViewModel = obtainViewModel(this)

    // Initial value
    Glide.with(this)
      .load(ColorDrawable(Color.GRAY))
      .circleCrop()
      .into(binding.imgAvatar)
    binding.tvName.text = getString(R.string.dots_placeholder)
    binding.tvUsername.text = getString(R.string.dots_placeholder)
    binding.tvBio.text = getString(R.string.dots_placeholder)
    binding.tvRepositoryCount.text = getString(R.string.dots_placeholder)
    binding.tvFollowingCount.text = getString(R.string.dots_placeholder)
    binding.tvFollowerCount.text = getString(R.string.dots_placeholder)

    binding.btnShare.setOnClickListener(this)
    binding.btnViewGithub.setOnClickListener(this)
    binding.fabFavorite.setOnClickListener(this)


    viewModel.getUser(username)

    // Observer
    viewModel.user.observe(this, { user ->
      this.user = user
      showFabFavoriteButton()

      Glide.with(this)
        .load(user.avatarUrl)
        .circleCrop()
        .placeholder(R.drawable.placeholder_circle)
        .into(binding.imgAvatar)

      binding.apply {
        tvUsername.text = user.login
        tvName.text = user.name
        tvCompany.text = user.company
        tvBio.text = user.bio?.toString()
        tvRepositoryCount.text = user.publicRepos.toString()
        tvFollowerCount.text = user.followers.toString()
        tvFollowingCount.text = user.following.toString()
      }
    })

    favoriteViewModel.getUserByUsername(username).observe(this, { user ->
      favoriteUser = user

      if (user != null) {
        setFavoriteFab(true)
      } else {
        setFavoriteFab(false)
      }
    })
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return super.onSupportNavigateUp()
  }

  private fun shareGithubAccount() {
    val intent = Intent(Intent.ACTION_SEND).apply {
      type = "text/plain"
      putExtra(Intent.EXTRA_SUBJECT, "Github Account")
      putExtra(Intent.EXTRA_TEXT, "https://github.com/${username}")
    }

    startActivity(Intent.createChooser(intent, "Share URL"))
  }

  override fun onClick(v: View?) {
    when(v?.id) {
      R.id.btn_share -> shareGithubAccount()
      R.id.btn_view_github -> {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/${username}")))
      }
      R.id.fab_favorite -> {
        if (favoriteUser != null) {
          favoriteViewModel.delete(favoriteUser!!)
        } else {
          val favoriteUser = FavoriteUser()
          favoriteUser.avatar_url = user.avatarUrl
          favoriteUser.login = username
          favoriteUser.createdAt = DateHelper.getCurrentDate()

          favoriteViewModel.insert(favoriteUser)
          Toast.makeText(this, "User added to favorite", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  private fun showFabFavoriteButton() {
    binding.fabFavorite.visibility = View.VISIBLE
  }

  private fun setFavoriteFab(isFavorite: Boolean) {
    if (isFavorite) {
      binding.fabFavorite.backgroundTintList = ColorStateList.valueOf(Color.RED)
      binding.fabFavorite.setColorFilter(Color.WHITE)
    } else {
      binding.fabFavorite.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
      binding.fabFavorite.setColorFilter(Color.GRAY)
    }
  }

  private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
    val factory = ViewModelFactory.getInstance(activity.application)
    return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
  }

  companion object {
    const val EXTRA_USERNAME = "extra_username"

    @StringRes
    private val TAB_TITLES = intArrayOf(
      R.string.tab_following,
      R.string.tab_follower
    )
  }
}