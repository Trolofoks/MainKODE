package com.honey.mainkode.ui.fragments.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.honey.mainkode.adapter.PeoplesAdapter
import com.honey.mainkode.base.BaseFragment
import com.honey.mainkode.databinding.FragmentMainBinding
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding, MainFragmentViewModel>(
    FragmentMainBinding::inflate,
    MainFragmentViewModel::class
) {
    private val adapter = PeoplesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        binding.recyclerView.setAdapter(adapter)
        binding.recyclerView.addVeiledItems(15)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.personsToShowState.collect(){peoples ->
                if (peoples.isNotEmpty()) {
                    binding.recyclerView.unVeil()
                    adapter.submitList(peoples)
                }
            }
        }
    }
}