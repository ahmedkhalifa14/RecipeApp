package com.example.recipeapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.adapter.CategoryAdapter
import com.example.recipeapp.adapter.MealsAdapter
import com.example.recipeapp.databinding.FragmentHomeBinding
import com.example.recipeapp.ui.MainActivity
import com.example.recipeapp.util.Resource
import com.example.recipeapp.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var mealsAdapter: MealsAdapter
    private val homeViewModel: HomeViewModel by lazy { (activity as MainActivity).viewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoryAdapterRecyclerView()
        setupMealsAdapterRecyclerView()
        moveToSearchFragment()

        categoryAdapter.setOnItemClickListener { categoryName ->
            homeViewModel.getMealList(categoryName.strCategory)
            homeViewModel.mealLiveData.observe(viewLifecycleOwner) { it ->
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { mealResponse ->
                            mealsAdapter.differ.submitList(mealResponse.meals.toList())

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
        mealsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("meal", it)

            }
            findNavController().navigate(
                R.id.action_homeFragment_to_mealsFragment,
                bundle
            )

        }

        homeViewModel.getCategoryList()
        homeViewModel.categoryLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {

                    it.data?.let { response ->
                        categoryAdapter.differ.submitList(response.categories.toList())
                    }

                }
                is Resource.Error -> {
                    it.message?.let { message ->
                        Log.d("error", message)
                        Toast.makeText(activity, "An error occurred: $message", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                is Resource.Loading -> {

                }
            }
        }



        homeViewModel.getMealList("Beef")
        homeViewModel.mealLiveData.observe(viewLifecycleOwner) { it ->
            when (it) {
                is Resource.Success -> {
                    it.data?.let { mealResponse ->
                        mealsAdapter.differ.submitList(mealResponse.meals.toList())

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

    private fun moveToSearchFragment() {
        binding.searchBar.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun setupMealsAdapterRecyclerView() {
        mealsAdapter = MealsAdapter()
        binding.MealsRV.apply {
            adapter = mealsAdapter
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            setHasFixedSize(true)
        }
    }

    private fun setupCategoryAdapterRecyclerView() {
        categoryAdapter = CategoryAdapter()
        binding.categoryRV.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)


        }

    }
}