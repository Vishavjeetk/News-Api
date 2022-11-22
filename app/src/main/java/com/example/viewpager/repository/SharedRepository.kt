package com.example.viewpager.repository

import android.util.Log
import com.example.viewpager.RetrofitInstance
import com.example.viewpager.models.NewsModel

class SharedRepository {
    suspend fun getAllNews(country: String, category: String): NewsModel? {

        val response = RetrofitInstance.apiClient.getAllNews(country,category)
        if (response.isSuccessful) {
            Log.d("RESPONSE", "getAllNews: ${response.body()}")
            return response.body()
        }
        if (!response.isSuccessful) {
            Log.d("NO", "getAllNews: No")
        }
        return null
    }

}
