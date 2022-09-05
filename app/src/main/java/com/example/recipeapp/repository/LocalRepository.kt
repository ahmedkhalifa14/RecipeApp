package com.example.recipeapp.repository

import com.example.recipeapp.database.MealDatabase
import com.example.recipeapp.model.Meal

class LocalRepository(val db:MealDatabase) {
    suspend fun updateOrInsertMeal(meal: Meal) = db.mealDao().updateOrInsertMeal(meal)
    fun getSavedMeals() = db.mealDao().getAllMeals()
    suspend fun deleteMeal(meal: Meal) = db.mealDao().deleteMeal(meal)
}