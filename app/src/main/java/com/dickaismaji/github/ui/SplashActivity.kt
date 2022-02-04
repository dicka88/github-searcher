package com.dickaismaji.github.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dickaismaji.github.MainActivity
import com.dickaismaji.github.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
  companion object {
    const val SPLASH_DELAY: Long = 1500
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

    Handler().postDelayed({
      startActivity(Intent(this, MainActivity::class.java))
      finish()
    }, SPLASH_DELAY)
  }
}