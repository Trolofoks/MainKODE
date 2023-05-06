package com.honey.mainkode.ui.fragments.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.honey.mainkode.R
import com.honey.mainkode.adapter.PeoplesAdapter
import com.honey.mainkode.base.BaseFragment
import com.honey.mainkode.databinding.FragmentMainBinding
import com.honey.mainkode.model.Department
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
            viewModel.peoplesToShowState.collect(){ peoples ->
                peoples?.let {
                    binding.recyclerView.unVeil()
                    adapter.submitList(peoples)
                    binding.includeEmptyList.root.visibility = if (peoples.isEmpty()) View.VISIBLE else View.GONE
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
        lifecycleScope.launch{
            viewModel.tabPosState.collect{tab->
                tab?.let {
                    binding.include.tabLayout.selectTab(tab)
                }
            }
        }


        binding.apply {
            editTextSearch.setOnFocusChangeListener { _, focused ->
                if (focused){
                    textButtonCancel.visibility = View.VISIBLE
                    buttonSearch.setColorFilter(R.color.black)
                } else {
                    textButtonCancel.visibility = View.GONE
                    buttonSearch.clearColorFilter()
                }
            }

            textButtonCancel.setOnClickListener {
                editTextSearch.clearFocus()
                editTextSearch.setText("")
                val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(editTextSearch.windowToken, 0)
            }

            editTextSearch.addTextChangedListener {value->
                viewModel.setSearchField(value.toString())
            }

            include.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewModel.setSelectTab(tab)
                }
                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }

    }
}