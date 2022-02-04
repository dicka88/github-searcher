package com.dickaismaji.github.service

import com.dickaismaji.github.models.response.search.user.UserItem
import com.dickaismaji.github.models.response.user.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
  @GET("users/{username}")
  fun getUser(
    @Path("username") username: String
  ): Call<UserResponse>

  @GET("users/{username}/followers")
  fun getUserFollowers(
    @Path("username") username: String
  ): Call<List<UserItem>>

  @GET("users/{username}/following")
  fun getUserFollowings(
    @Path("username") username: String
  ): Call<List<UserItem>>
}
