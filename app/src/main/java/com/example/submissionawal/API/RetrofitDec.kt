package com.example.submissionawal.API

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit.*
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitDec {
    private const val BASE_URL = "https://api.github.com/"

    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    val retrofit = Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(ConfigApi::class.java)
}