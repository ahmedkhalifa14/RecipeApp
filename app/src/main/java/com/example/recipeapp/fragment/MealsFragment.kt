package com.example.recipeapp.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.FragmentMealsBinding
import com.example.recipeapp.model.MealDetails
import com.example.recipeapp.model.MealsDetailsList
import com.example.recipeapp.ui.MainActivity
import com.example.recipeapp.util.Resource
import com.example.recipeapp.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.meal_layout.view.*
import java.lang.StringBuilder


class MealsFragment : Fragment() {
    private val args: MealsFragmentArgs by navArgs()

    private lateinit var binding: FragmentMealsBinding
    private val homeViewModel: HomeViewModel by lazy { (activity as MainActivity).viewModel }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val meal = args.meal
        binding.btnSave.setOnClickListener{
            homeViewModel.saveMeal(meal)
            Snackbar.make(view,"Meal saved successfully", Snackbar.LENGTH_SHORT).show()
        }
        homeViewModel.getMeal(meal.idMeal)
        homeViewModel.mealDetailsLiveData.observe(viewLifecycleOwner) { it ->
            when (it) {
                is Resource.Success -> {
                    it.data?.let { mealResponse ->
                        val mealsDetails=mealResponse.mealsDetailsList.toList()
                        val mealDetails = mealsDetails[0]
                        setData(mealDetails)
                        openYoutube(mealDetails)

                    }

                }
                is Resource.Error -> {
                    it.message?.let { message ->
                        Toast.makeText(activity,
                            "An error occurred: ${it.message}",
                            Toast.LENGTH_LONG)
                            .show()
                    }
                }
                is Resource.Loading -> {

                }
            }
        }
    }

    private fun setData(mealResponse: MealDetails) {
        binding.collapsingToolbar.title = mealResponse.strMeal
        binding.country.text=mealResponse.strArea
        binding.category.text=mealResponse.strCategory
        binding.instructions.text=mealResponse.strInstructions
        addIngredientToIngredients(mealResponse)
        addMeasures(mealResponse)
        Glide.with(this@MealsFragment).load(mealResponse.strMealThumb)
            .into(binding.imgMealDetail)
    }
    private fun putIngredient(gradients: String) {
        binding.ingredient.text = gradients
    }

    private fun getIngredient(ingredients: MutableList<String>) {
        val ingredientText = StringBuilder()
        for (ingredient: String in ingredients) {
            if (ingredient != " " && ingredient.isNotEmpty()) {
                ingredientText.append("\n \u2022$ingredient")
            }
            putIngredient(ingredientText.toString())
        }
    }

    private fun addIngredientToIngredients(meal: MealDetails) {
        val ingredients = mutableListOf<String>()
        ingredients.add(meal.strIngredient1)
        ingredients.add(meal.strIngredient2)
        ingredients.add(meal.strIngredient3)
        ingredients.add(meal.strIngredient4)
        ingredients.add(meal.strIngredient5)
        ingredients.add(meal.strIngredient6)
        ingredients.add(meal.strIngredient7)
        ingredients.add(meal.strIngredient8)
        ingredients.add(meal.strIngredient9)
        ingredients.add(meal.strIngredient10)
        ingredients.add(meal.strIngredient11)
        ingredients.add(meal.strIngredient12)
        ingredients.add(meal.strIngredient13)
        ingredients.add(meal.strIngredient14)
        ingredients.add(meal.strIngredient15)
        ingredients.add(meal.strIngredient16)
        ingredients.add(meal.strIngredient17)
        ingredients.add(meal.strIngredient18)
        ingredients.add(meal.strIngredient19)
        ingredients.add(meal.strIngredient20)

        getIngredient(ingredients)
    }

    private fun addMeasures(meal: MealDetails) {
        val measures = mutableListOf<String>()
        measures.add(meal.strMeasure1)
        measures.add(meal.strMeasure2)
        measures.add(meal.strMeasure3)
        measures.add(meal.strMeasure4)
        measures.add(meal.strMeasure5)
        measures.add(meal.strMeasure6)
        measures.add(meal.strMeasure7)
        measures.add(meal.strMeasure8)
        measures.add(meal.strMeasure9)
        measures.add(meal.strMeasure10)
        measures.add(meal.strMeasure11)
        measures.add(meal.strMeasure12)
        measures.add(meal.strMeasure13)
        measures.add(meal.strMeasure14)
        measures.add(meal.strMeasure15)
        measures.add(meal.strMeasure16)
        measures.add(meal.strMeasure17)
        measures.add(meal.strMeasure18)
        measures.add(meal.strMeasure19)
        measures.add(meal.strMeasure20)

        getMeasures(measures)
    }

    private fun getMeasures(measuresList: MutableList<String>) {
        val measureText = StringBuilder()
        for (measure: String in measuresList) {
            if (measure != " " && measure.isNotEmpty()) {
                measureText.append("\n \u2022$measure")
            }
            putMeasuresText(measureText.toString())
        }
    }

    private fun putMeasuresText(measureText: String) {
        binding.measure.text = measureText
    }
    private fun openYoutube(meal: MealDetails) {
        binding.youtube.setOnClickListener {
            implicitIntent(meal.strYoutube)
        }
    }

    private fun implicitIntent(uri: String) {
        val implicitIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        activity?.startActivity(implicitIntent)
    }
}