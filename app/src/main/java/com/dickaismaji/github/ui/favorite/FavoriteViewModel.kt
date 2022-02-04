package com.dickaismaji.github.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dickaismaji.github.database.FavoriteUser
import com.dickaismaji.github.repository.local.FavoriteUserRepository

class FavoriteViewModel(application: Application): ViewModel() {
  private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

  fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavoriteUser()

  fun getUserByUsername(username: String): LiveData<FavoriteUser> = mFavoriteUserRepository.getUserByUsername(username)

  fun insert(user: FavoriteUser) {
    mFavoriteUserRepository.insert(user)
  }

  fun delete(user: FavoriteUser) {
    mFavoriteUserRepository.delete(user)
  }
}