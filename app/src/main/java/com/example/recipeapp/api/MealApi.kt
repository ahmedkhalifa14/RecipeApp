package com.example.recipeapp.api

import com.example.recipeapp.model.*
import retrofit2.Response
import retrofit2.http.*

interface MealApi {

    @GET("categories.php")
    suspend fun getCategoryList(): Response<Categories>

    @GET("filter.php")
    suspend fun getMealsList( @Query("c") category:String): Response<Meals>

    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") mealId:String) : Response<MealsDetailsList>

    @GET("search.php")
    suspend fun getSearchedMeals(@Query("s") searchedText: String): Response<MealsDetailsList>


}