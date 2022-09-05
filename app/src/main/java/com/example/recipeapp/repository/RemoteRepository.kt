package com.example.recipeapp.repository

import com.example.recipeapp.api.RetrofitInstance
import com.example.recipeapp.model.Categories
import retrofit2.Response

class RemoteRepository {
    suspend fun getMealDetails(idMeal:String)=
        RetrofitInstance.API.getMealDetails(idMeal)
    suspend fun getCategoryList()=
        RetrofitInstance.API.getCategoryList()
    suspend fun getMealsList(category:String)=
        RetrofitInstance.API.getMealsList(category)
    suspend fun getSearchedMeals(searchedText: String)=
        RetrofitInstance.API.getSearchedMeals(searchedText)

}
