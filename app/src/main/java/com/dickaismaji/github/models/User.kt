package com.dickaismaji.github.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
  var username: String,
  var name: String,
  var avatar: Int,
  var followerCount: Int,
  var followingCount: Int,
  var repositoryCount: Int,
  var location: String,
  var company: String,
): Parcelable