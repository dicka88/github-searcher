package com.dickaismaji.github.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dickaismaji.github.ui.favorite.FavoriteViewModel
import com.dickaismaji.github.ui.search.SearchViewModel
import com.dickaismaji.github.ui.user.UserGithubViewModel

class ViewModelFactory(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
  companion object {
    @Volatile
    private var INSTANCE: ViewModelFactory? = null
    @JvmStatic
    fun getInstance(application: Application): ViewModelFactory {
      if (INSTANCE == null) {
        synchronized(ViewModelFactory::class.java) {
          INSTANCE = ViewModelFactory(application)
        }
      }
      return INSTANCE as ViewModelFactory
    }
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
      return SearchViewModel() as T
    } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
      return FavoriteViewModel(mApplication) as T
    }

    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
  }
}