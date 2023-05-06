package com.honey.mainkode.ui.fragments.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.honey.mainkode.R
import com.honey.mainkode.adapter.PeoplesAdapter
import com.honey.mainkode.base.BaseFragment
import com.honey.mainkode.databinding.FragmentMainBinding
import com.honey.mainkode.model.Constance
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
    FragmentMainBinding::inflate,
    MainViewModel::class
) {
    private val adapter = PeoplesAdapter()
    private lateinit var controller : NavController

    override fun onResume() {
        super.onResume()
        viewModel.setActiveScreen(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = findNavController()
        binding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        binding.recyclerView.setAdapter(adapter)
        binding.recyclerView.addVeiledItems(15)

        lifecycleScope.launch {
            viewModel.personsToShowState.collect(){peoples ->
                if (peoples.isNotEmpty()) {
                    binding.recyclerView.unVeil()
                    adapter.submitList(peoples)
                }
            }
        }

        lifecycleScope.launch{
            viewModel.errorShowShar.collect{error->
                if (error){
                    viewModel.setActiveScreen(false)
                    controller.navigate(R.id.errorFragment)
                }

            }
        }

        binding.editTextSearch.setOnFocusChangeListener { _, focused ->
            if (focused){
                binding.textButtonCancel.visibility = View.VISIBLE
                binding.buttonSearch.setColorFilter(R.color.black)
            } else {
                binding.textButtonCancel.visibility = View.GONE
                binding.buttonSearch.clearColorFilter()
            }
        }

        binding.textButtonCancel.setOnClickListener {
            binding.editTextSearch.clearFocus()
            binding.editTextSearch.setText("")
            val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.editTextSearch.windowToken, 0)
        }
    }
}