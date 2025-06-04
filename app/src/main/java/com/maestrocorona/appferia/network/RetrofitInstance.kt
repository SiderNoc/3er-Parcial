package com.maestrocorona.appferia.network

import com.maestrocorona.appferia.api.TheCatApiService


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.thecatapi.com/"

    val api: TheCatApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheCatApiService::class.java)
    }
}