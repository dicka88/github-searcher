package com.dickaismaji.github.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteUser(
  @PrimaryKey(autoGenerate = true)
  var id: Int = 0,
  var login: String? = null,
  var avatar_url: String? = null,
  var createdAt: String? = null
): Parcelable