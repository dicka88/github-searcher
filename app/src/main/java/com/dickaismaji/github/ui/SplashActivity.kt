package com.dickaismaji.github.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dickaismaji.github.R
import com.dickaismaji.github.ui.search.SearchActivity

class SplashActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

    Handler(Looper.getMainLooper()).postDelayed({
      startActivity(Intent(this, SearchActivity::class.java))
      finish()
    }, SPLASH_DELAY)
  }

  companion object {
    const val SPLASH_DELAY: Long = 700
  }
}
