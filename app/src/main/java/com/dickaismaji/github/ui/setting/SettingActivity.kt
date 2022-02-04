package com.dickaismaji.github.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.dickaismaji.github.databinding.ActivitySettingBinding
import com.dickaismaji.github.helper.UserPreference

class SettingActivity : AppCompatActivity() {
  private lateinit var binding: ActivitySettingBinding
  private lateinit var mUserPreference: UserPreference

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivitySettingBinding.inflate(layoutInflater)

    setContentView(binding.root)

    mUserPreference = UserPreference(this)

    supportActionBar?.apply {
      title = getString(R.id.setting)
      setDisplayHomeAsUpEnabled(true)
    }

    binding.switchTheme.isChecked = mUserPreference.isDarkMode()

    binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
      if (isChecked) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
      } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
      }

      setThemeSetting(isChecked)
    }
  }

  private fun setThemeSetting(isActive: Boolean) {
    mUserPreference.setDarkMode(isActive)
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return super.onSupportNavigateUp()
  }
}