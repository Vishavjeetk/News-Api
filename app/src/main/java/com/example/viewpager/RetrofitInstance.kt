package com.example.viewpager

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val newsInterface: NewsInterface by lazy {
        retrofit.create(NewsInterface::class.java)
    }

    val apiClient = ApiClient(newsInterface)

}