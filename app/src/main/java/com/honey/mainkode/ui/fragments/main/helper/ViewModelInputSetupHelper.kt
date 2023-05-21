package com.honey.mainkode.ui.fragments.main.helper

import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.honey.mainkode.R
import com.honey.mainkode.adapter.PeoplesAdapter
import com.honey.mainkode.databinding.FragmentMainBinding
import com.honey.mainkode.ui.fragments.main.MainViewModel
import kotlinx.coroutines.launch

class ViewModelInputSetupHelper(
    private val viewModel: MainViewModel,
    private val adapter: PeoplesAdapter,
    private val lifecycleScope: LifecycleCoroutineScope,
    private val binding: FragmentMainBinding,
    private val controller: NavController
) {
    fun setupObservers() {
        lifecycleScope.launch {
            viewModel.peoplesToShowState.collect { peoples ->
                adapter.submitList(peoples)
                if (peoples.isNotEmpty()) {
                    binding.includeEmptyList.root.visibility = View.GONE
                } else {
                    binding.includeEmptyList.root.visibility = View.VISIBLE
                }
            }
        }

        lifecycleScope.launch {
            viewModel.skeletonsShowState.collect { visible ->
                if (visible) {
                    binding.recyclerView.veil()
                    binding.includeEmptyList.root.visibility = View.GONE
                } else {
                    binding.recyclerView.unVeil()
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }

        lifecycleScope.launch {
            viewModel.errorShowShar.collect { error ->
                if (error) {
                    viewModel.setActiveScreen(false)
                    controller.navigate(R.id.errorFragment)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.tabPosState.collect { tab ->
                tab?.let {
                    binding.include.tabLayout.selectTab(tab)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.sortByState.collect { sort ->
                adapter.dobSort(sort)
            }
        }
    }
}
