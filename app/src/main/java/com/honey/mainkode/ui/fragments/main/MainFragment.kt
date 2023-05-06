package com.honey.mainkode.ui.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.View
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
    }
}