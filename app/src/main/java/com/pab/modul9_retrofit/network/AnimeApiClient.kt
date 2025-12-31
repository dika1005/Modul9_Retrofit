package com.pab.modul9_retrofit.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AnimeApiClient {
    private const val BASE_URL = "https://api.ryzumi.vip/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: AnimeApiService by lazy {
        retrofit.create(AnimeApiService::class.java)
    }
}
