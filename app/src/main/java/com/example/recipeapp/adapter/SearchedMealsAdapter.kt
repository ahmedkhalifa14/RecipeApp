package com.example.recipeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.SearchedMealLayoutBinding
import com.example.recipeapp.fragment.SearchFragmentDirections
import com.example.recipeapp.model.Meal
import com.example.recipeapp.model.MealDetails

class SearchedMealsAdapter(val context: Context) :
    ListAdapter<MealDetails, SearchedMealsAdapter.SearchedMealsVH>(SearchedMealsListDiff()) {

    private var _binding: SearchedMealLayoutBinding?=null
    private val binding
        get() = _binding!!

    private lateinit var navController: NavController
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchedMealsAdapter.SearchedMealsVH {
        val inflater = LayoutInflater.from(parent.context)
        _binding= SearchedMealLayoutBinding.inflate(inflater,parent,false)
        return SearchedMealsVH(binding)
    }

    override fun onBindViewHolder(holder: SearchedMealsAdapter.SearchedMealsVH, position: Int) {
        val meal = getItem(position)
        val searchedMealImageUrl = meal.strMealThumb
        val searchedMealName = meal.strMeal
        val _meal: Meal=Meal(meal.idMeal,meal.strMeal,meal.strMealThumb)

        holder.binding.searchedMealName.text = searchedMealName
        Glide.with(context).load(searchedMealImageUrl).into(holder.binding.searchedImageId)

        holder.itemView.setOnClickListener {
            navController= Navigation.findNavController(it)
            val action = SearchFragmentDirections.actionSearchFragmentToMealsFragment(_meal)
            navController.navigate(action)

        }
    }

    class SearchedMealsVH(val binding: SearchedMealLayoutBinding) : RecyclerView.ViewHolder(binding.root) {}

    companion object {
        private const val TAG = "SearchedMealsAdapter"
    }

    class SearchedMealsListDiff : DiffUtil.ItemCallback<MealDetails>() {
        override fun areItemsTheSame(oldItem: MealDetails, newItem: MealDetails): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealDetails, newItem: MealDetails): Boolean {
            return oldItem == newItem
        }
    }
}