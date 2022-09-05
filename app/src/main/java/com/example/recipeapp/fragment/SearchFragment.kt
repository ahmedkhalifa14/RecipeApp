package com.example.recipeapp.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.adapter.SearchedMealsAdapter
import com.example.recipeapp.databinding.FragmentSearchBinding
import com.example.recipeapp.ui.MainActivity
import com.example.recipeapp.util.*
import com.example.recipeapp.viewmodel.HomeViewModel


class SearchFragment : Fragment() {


    private lateinit var searchedMealsAdapter: SearchedMealsAdapter
    private val homeViewModel: HomeViewModel by lazy { (activity as MainActivity).viewModel }

    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding!!





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVars()
        searchAction()
        initRecyclerView()
        moveToHomeFragment()
    }

    private fun initVars() {
        showKeyboard(requireContext(), binding.searchBox)
        searchedMealsAdapter = SearchedMealsAdapter(requireContext())
    }

    private fun searchAction() {
        binding.searchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                homeViewModel.getSearchedMeals(p0.toString())
                homeViewModel.searchLiveData.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Success -> {
                            it.data.let { searchResult ->
                                binding.noMealCard.showIf {
                                    searchResult == null
                                }
                                searchedMealsAdapter.submitList(searchResult!!.mealsDetailsList)
                                binding.searchProgressBar.hide()
                                binding.view.hide()
                            }


                        }
                        is Resource.Error -> {
                            it.message?.let { message ->
                                Log.d("error", message)
                                Toast.makeText(activity,
                                    "An error occurred: $message",
                                    Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                        is Resource.Loading -> {
                            binding.searchProgressBar.show()
                            binding.view.show()
                        }
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun initRecyclerView() {
        binding.searchedMealsContainer.apply {
            setHasFixedSize(true)
            adapter = searchedMealsAdapter
        }
    }
    private fun moveToHomeFragment() {
        binding.backBtnId.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_homeFragment)
        }
    }


    companion object {
        private const val TAG = "SearchFragment"
    }
}
