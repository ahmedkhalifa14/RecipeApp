package com.example.recipeapp.model

import com.google.gson.annotations.SerializedName

data class MealsDetailsList (
    @SerializedName("meals")
    val mealsDetailsList: MutableList<MealDetails>
)
