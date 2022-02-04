package com.dickaismaji.github.repository.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.dickaismaji.github.database.FavoriteUser
import com.dickaismaji.github.database.FavoriteUserDao
import com.dickaismaji.github.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
  private val mFavoriteUserDao: FavoriteUserDao
  private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

  init {
    val db = FavoriteUserRoomDatabase.getDatabase(application)
    mFavoriteUserDao = db.favoriteUserDao()
  }

  fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllFavoriteUser()

  fun getUserByUsername(username: String): LiveData<FavoriteUser> = mFavoriteUserDao.getUserByUsername(username)

  fun insert(user: FavoriteUser) {
    executorService.execute { mFavoriteUserDao.insert(user) }
  }

  fun delete(user: FavoriteUser) {
    executorService.execute { mFavoriteUserDao.delete(user) }
  }
}