package com.example.recipeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipeapp.model.Meal

@Database(
    entities = [Meal::class],
    version = 1,
    exportSchema = false
)
abstract class MealDatabase: RoomDatabase() {
    abstract fun mealDao(): MealDao
    companion object{
        @Volatile
        private var mealInstance: MealDatabase?=null
        private val LOCK = Any()
        operator fun invoke(context: Context)= mealInstance ?: synchronized(LOCK){
            mealInstance ?: createDatabase(context).also{ mealInstance = it}
        }

        private fun createDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                MealDatabase::class.java,
                "meals_db"
            ).fallbackToDestructiveMigration()
                .build()
    }
}