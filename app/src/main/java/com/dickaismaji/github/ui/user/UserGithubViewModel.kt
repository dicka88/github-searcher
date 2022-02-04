package com.dickaismaji.github.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dickaismaji.github.config.ApiConfig
import com.dickaismaji.github.models.response.search.user.UserItem
import com.dickaismaji.github.models.response.user.UserResponse
import com.dickaismaji.github.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserGithubViewModel: ViewModel() {
  private val _user = MutableLiveData<UserResponse>()

  val user: LiveData<UserResponse> = _user
  private val _isUserLoading = MutableLiveData<Boolean>()
  val isUserLoading: LiveData<Boolean> = _isUserLoading
  private val _userFollowers = MutableLiveData<List<UserItem>>()

  val userFollowers: LiveData<List<UserItem>> = _userFollowers
  private val _isUserFollowerLoading = MutableLiveData<Boolean>()
  val isUserFollowerLoading: LiveData<Boolean> = _isUserFollowerLoading
  private val _userFollowings = MutableLiveData<List<UserItem>>()

  val userFollowings: LiveData<List<UserItem>> = _userFollowings
  private val _isUserFollowingLoading = MutableLiveData<Boolean>()
  val isUserFollowingLoading: LiveData<Boolean> = _isUserFollowingLoading

  private val apiService = ApiConfig.getApiService().create(UserService::class.java)

  fun getUser(username: String) {
    val client = apiService.getUser(username)
    _isUserLoading.value = true

    client.enqueue(object: Callback<UserResponse> {
      override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
        val responseBody = response.body()
        _isUserLoading.value = false

        if(response.isSuccessful) {
          _user.value = responseBody!!
        }
      }

      override fun onFailure(call: Call<UserResponse>, t: Throwable) {
        _isUserLoading.value = false
      }
    })
  }

  fun getFollowers(username: String) {
    val client = apiService.getUserFollowers(username)
    _isUserFollowerLoading.value = true

    client.enqueue(object: Callback<List<UserItem>> {
      override fun onResponse(
        call: Call<List<UserItem>>,
        response: Response<List<UserItem>>
      ) {
        val responseBody = response.body()
        _isUserFollowerLoading.value = false

        if(response.isSuccessful) {
          _userFollowers.value = responseBody!!
        }
      }

      override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
        _isUserFollowerLoading.value = false
      }
    })
  }

  fun getFollowings(username: String) {
    val client = apiService.getUserFollowings(username)
    _isUserFollowingLoading.value = true

    client.enqueue(object: Callback<List<UserItem>> {
      override fun onResponse(
        call: Call<List<UserItem>>,
        response: Response<List<UserItem>>
      ) {
        val responseBody = response.body()
        _isUserFollowingLoading.value = false

        if(response.isSuccessful) {
          _userFollowings.value = responseBody!!
        }
      }

      override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
        _isUserFollowingLoading.value = false
      }
    })
  }
}