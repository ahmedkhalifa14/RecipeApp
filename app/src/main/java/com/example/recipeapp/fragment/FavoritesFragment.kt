package com.example.recipeapp.fragment

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recipeapp.R
import com.example.recipeapp.adapter.CategoryAdapter
import com.example.recipeapp.adapter.MealsAdapter
import com.example.recipeapp.databinding.FragmentFavoritesBinding
import com.example.recipeapp.databinding.FragmentHomeBinding
import com.example.recipeapp.ui.MainActivity
import com.example.recipeapp.viewmodel.HomeViewModel

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    lateinit var mealsAdapter: MealsAdapter
    private val homeViewModel: HomeViewModel by lazy { (activity as MainActivity).viewModel }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}