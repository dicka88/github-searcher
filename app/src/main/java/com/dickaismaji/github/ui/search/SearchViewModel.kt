package com.dickaismaji.github.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dickaismaji.github.config.ApiConfig
import com.dickaismaji.github.models.response.search.user.UserItem
import com.dickaismaji.github.models.response.search.user.UserSearchResponse
import com.dickaismaji.github.service.SearchService
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class SearchViewModel: ViewModel() {
  private val _isLoading = MutableLiveData<Boolean>()

  val isLoading: LiveData<Boolean> = _isLoading
  private val _listUsers = MutableLiveData<List<UserItem>>()

  val listUsers: LiveData<List<UserItem>> = _listUsers

  fun getUserList(query: String) {
    _isLoading.value = true

    val apiService = ApiConfig.getApiService().create(SearchService::class.java)
    val client = apiService.getSearchUsers(query)

    client.enqueue(object: Callback<UserSearchResponse> {
      override fun onResponse(
        call: Call<UserSearchResponse>,
        response: Response<UserSearchResponse>
      ) {
        _isLoading.value = false

        if(response.isSuccessful) {
          val responseBody = response.body()

          if(responseBody != null) {
            _listUsers.value = responseBody.items!!
          }
        }
      }

      override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
        _isLoading.value = false
        Log.d(TAG, "onFailure: ${t.message}")
      }
    })
  }

  companion object {
    private const val TAG = "SearchViewModel"
  }
}