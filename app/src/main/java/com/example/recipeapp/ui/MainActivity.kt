package com.example.recipeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.recipeapp.R
import com.example.recipeapp.database.MealDatabase
import com.example.recipeapp.repository.LocalRepository
import com.example.recipeapp.repository.RemoteRepository
import com.example.recipeapp.viewmodel.HomeViewModel
import com.example.recipeapp.viewmodel.MainViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    val remoteRepository by lazy { RemoteRepository() }
    val localRepository by lazy { LocalRepository(MealDatabase(this)) }
    val viewModelFactory by lazy { MainViewModelFactory(this,remoteRepository,localRepository) }
    val viewModel: HomeViewModel by lazy{ ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView= findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navController = findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)
    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        when(destination.id){
            R.id.homeFragment->{
                bottomNavigationView.visibility= View.VISIBLE
            }
            R.id.favoritesFragment->{
                bottomNavigationView.visibility=View.VISIBLE
            }
            R.id.categoriesFragment->{
                bottomNavigationView.visibility=View.VISIBLE
            }
            else->{
                bottomNavigationView.visibility=View.GONE
            }



        }
    }

}
}