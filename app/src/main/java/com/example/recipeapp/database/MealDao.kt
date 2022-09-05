package com.example.recipeapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recipeapp.model.Meal
@Dao
interface MealDao {
     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun updateOrInsertMeal(meal:Meal)
     @Query("SELECT * FROM meal")
     fun getAllMeals(): LiveData<List<Meal>>
     @Delete
     suspend fun deleteMeal(meal:Meal)
}