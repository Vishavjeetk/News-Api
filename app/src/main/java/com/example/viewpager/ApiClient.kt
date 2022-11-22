package com.example.viewpager

import com.example.viewpager.models.NewsModel
import retrofit2.Response

class ApiClient(private val newsInterface: NewsInterface) {

    suspend fun getAllNews(country: String, category: String): Response<NewsModel> {
        return newsInterface.getAllNews(country,category)
    }
}
