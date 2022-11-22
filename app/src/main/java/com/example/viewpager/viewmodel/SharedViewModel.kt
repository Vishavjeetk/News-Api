package com.example.viewpager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewpager.models.NewsModel
import com.example.viewpager.repository.SharedRepository
import kotlinx.coroutines.launch

class SharedViewModel: ViewModel() {

    private val repository = SharedRepository()

    private val mutableGetAllNews = MutableLiveData<NewsModel?>()
    val getAllNewsLiveData: LiveData<NewsModel?> = mutableGetAllNews

    fun getAllNewsFromSharedViewModel(country: String, category: String) {
        viewModelScope.launch {
            val news = repository.getAllNews(country,category)
            mutableGetAllNews.postValue(news)
        }
    }

}