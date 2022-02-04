package com.dickaismaji.github.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insert(favoriteUser: FavoriteUser)

  @Delete
  fun delete(favoriteUser: FavoriteUser)

  @Query("SELECT * FROM FavoriteUser WHERE login = :username")
  fun getUserByUsername(username: String): LiveData<FavoriteUser>

  @Query("SELECT * FROM FavoriteUser ORDER BY id DESC")
  fun getAllFavoriteUser(): LiveData<List<FavoriteUser>>
}