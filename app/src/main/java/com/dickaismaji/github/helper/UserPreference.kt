package com.dickaismaji.github.helper

import android.content.Context

class UserPreference(context: Context) {
  private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

  fun isDarkMode(): Boolean = preferences.getBoolean(IS_DARK_MODE, false)
  fun setDarkMode(isActive: Boolean) {
    val editor = preferences.edit()
    editor.putBoolean(IS_DARK_MODE, isActive)
    editor.apply()
  }

  companion object {
    private const val PREFS_NAME = "user_prefs"
    private const val IS_DARK_MODE = "is_light_mode"
  }
}