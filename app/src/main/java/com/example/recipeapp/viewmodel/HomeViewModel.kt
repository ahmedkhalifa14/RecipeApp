package com.example.recipeapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.model.*
import com.example.recipeapp.repository.LocalRepository
import com.example.recipeapp.repository.RemoteRepository
import com.example.recipeapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

private const val TAG = "HomeViewModel"

class HomeViewModel(val app: Context, private val remoteRepository: RemoteRepository,private val localRepository: LocalRepository) :
    ViewModel() {

    val mealDetailsLiveData: MutableLiveData<Resource<MealsDetailsList>> = MutableLiveData()
    private var mealDetailsResponse: MealsDetailsList? = null


    val searchLiveData: MutableLiveData<Resource<MealsDetailsList>> = MutableLiveData()
    private var searchResponse: MealsDetailsList? = null

    val categoryLiveData: MutableLiveData<Resource<Categories>> = MutableLiveData()
    private var categoriesResponse: Categories? = null

    val mealLiveData: MutableLiveData<Resource<Meals>> = MutableLiveData()
    private var mealsResponse: Meals? = null

    //Local

    fun saveMeal(meal:Meal) = viewModelScope.launch {
        localRepository.updateOrInsertMeal(meal)
    }

    fun getSavedMeals() = localRepository.getSavedMeals()
    fun deleteMeal(meal:Meal) = viewModelScope.launch {
        localRepository.deleteMeal(meal)
    }
    //Remote


    fun getMealList(category: String) = viewModelScope.launch {
        getMealListCall(category)
    }

    private suspend fun getMealListCall(category: String) {
        mealLiveData.postValue(Resource.Loading())

        try {

            if (hasInternetConnection(app)) {
                val response = remoteRepository.getMealsList(category)
                mealLiveData.postValue(handleMealResponse(response))
                Log.d(TAG, "getMealListCall: $response.")
            } else {
                mealLiveData.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            t.localizedMessage?.let { Log.d("tag", it) }
            when (t) {
                is IOException -> mealLiveData.postValue(Resource.Error("Network failure"))
                else -> mealLiveData.postValue(Resource.Error("Conversion Error"))

            }
        }

    }

    private fun handleMealResponse(response: Response<Meals>): Resource<Meals> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->

                if (mealsResponse == null) {
                    mealsResponse = resultResponse
                } else {
                    val oldMeal = mealsResponse?.meals
                    val newMeal = resultResponse.meals
                    oldMeal?.addAll(newMeal)
                }
                return Resource.Success(mealsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    fun getCategoryList() = viewModelScope.launch {
        getCategoryListCall()
    }

    private suspend fun getCategoryListCall() {
        categoryLiveData.postValue(Resource.Loading())

        try {

            if (hasInternetConnection(app)) {
                val response = remoteRepository.getCategoryList()
                categoryLiveData.postValue(handleCategoryResponse(response))
                Log.d(TAG, "getCategoryListCall: $response.")
            } else {
                categoryLiveData.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            t.localizedMessage?.let { Log.d("tag", it) }
            when (t) {
                is IOException -> categoryLiveData.postValue(Resource.Error("Network failure"))
                else -> categoryLiveData.postValue(Resource.Error("Conversion Error"))

            }
        }

    }

    private fun handleCategoryResponse(response: Response<Categories>): Resource<Categories> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->

                if (categoriesResponse == null) {
                    categoriesResponse = resultResponse
                } else {
                    val oldCategory = categoriesResponse?.categories
                    val newCategory = resultResponse.categories
                    oldCategory?.addAll(newCategory)
                }
                return Resource.Success(categoriesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    fun getMeal(idMeal: String) = viewModelScope.launch {
        getMealCall(idMeal)
    }

    private suspend fun getMealCall(idMeal: String) {
        mealDetailsLiveData.postValue(Resource.Loading())

        try {

            if (hasInternetConnection(app)) {
                val response = remoteRepository.getMealDetails(idMeal)
                mealDetailsLiveData.postValue(handleMealDetailsResponse(response))
            } else {
                mealDetailsLiveData.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            t.localizedMessage?.let { Log.d("tag", it) }
            when (t) {
                is IOException -> mealDetailsLiveData.postValue(Resource.Error("Network failure"))
                else -> mealDetailsLiveData.postValue(Resource.Error("Conversion Error"))

            }
        }

    }

    private fun handleMealDetailsResponse(response: Response<MealsDetailsList>): Resource<MealsDetailsList> {
        Log.d(TAG, "meal details response : ${response}.")
        Log.d(TAG, "meal details body : ${response.body()}.")

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->

                if (mealDetailsResponse == null) {
                    mealDetailsResponse = resultResponse
                }
                return Resource.Success(mealDetailsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }




    fun getSearchedMeals(searchedText: String) = viewModelScope.launch {
        getSearchedMeal(searchedText)
    }

    private suspend fun getSearchedMeal(searchedText: String) {
        searchLiveData.postValue(Resource.Loading())

        try {

            if (hasInternetConnection(app)) {
                val response = remoteRepository.getSearchedMeals(searchedText)
                searchLiveData.postValue(handleSearchResponse(response))
            } else {
                searchLiveData.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            t.localizedMessage?.let { Log.d("tag", it) }
            when (t) {
                is IOException -> searchLiveData.postValue(Resource.Error("Network failure"))
                else -> searchLiveData.postValue(Resource.Error("Conversion Error"))

            }
        }

    }

    private fun handleSearchResponse(response: Response<MealsDetailsList>): Resource<MealsDetailsList> {
        Log.d(TAG, "search response : ${response}.")
        Log.d(TAG, "search body : ${response.body()}.")

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->

                if (searchResponse == null) {
                    searchResponse = resultResponse
                }

                return Resource.Success(searchResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}