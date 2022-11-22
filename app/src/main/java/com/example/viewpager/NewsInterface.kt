package com.example.viewpager

import com.example.viewpager.models.NewsModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "aec542bffac2499698b2d0a7eacdd19b"

interface NewsInterface {

    @GET("v2/top-headlines?apiKey=$API_KEY")
    suspend fun getAllNews(@Query("country") country:String, @Query("category") category: String): Response<NewsModel>

}





//https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=b1fd53a2a4e34f0994f63177973f5462
