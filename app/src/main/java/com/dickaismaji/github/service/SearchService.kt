package com.dickaismaji.github.service

import com.dickaismaji.github.models.response.search.user.UserSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
  @GET("/search/users")
  fun getSearchUsers(
    @Query("q") q: String
  ): Call<UserSearchResponse>
}