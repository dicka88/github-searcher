package com.dickaismaji.github.config

import com.dickaismaji.github.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers

class ApiConfig {
  companion object {
    private const val TOKEN: String = BuildConfig.API_KEY

    @Headers("Authorization: token $TOKEN")
    fun getApiService(): Retrofit {
      val loggingInterceptor = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
      } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
      }

      val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
          val original = chain.request()
          val request = original.newBuilder()
            .addHeader("Authorization", TOKEN)
            .method(original.method, original.body)
            .build()

          chain.proceed(request)
        }
        .build()

      return Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    }
  }
}