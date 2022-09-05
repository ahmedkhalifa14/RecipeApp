package com.example.recipeapp.viewmodel

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.repository.LocalRepository
import com.example.recipeapp.repository.RemoteRepository

class MainViewModelFactory(val app: Context, private val remoteRepository: RemoteRepository,private val localRepository: LocalRepository):
ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        HomeViewModel(app,remoteRepository,localRepository) as T
}
