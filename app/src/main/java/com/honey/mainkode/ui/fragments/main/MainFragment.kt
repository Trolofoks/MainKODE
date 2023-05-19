package com.honey.mainkode.ui.fragments.main

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.honey.mainkode.R
import com.honey.mainkode.adapter.Listener
import com.honey.mainkode.adapter.PeoplesAdapter
import com.honey.mainkode.base.BaseFragment
import com.honey.mainkode.databinding.FragmentMainBinding
import com.honey.mainkode.databinding.PartBottomSheetDialogBinding
import com.honey.mainkode.model.Constance
import com.honey.model.SortBy
import com.honey.model.People
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(), Listener {
    private val adapter = PeoplesAdapter(this@MainFragment)
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
                adapter.submitList(peoples)
                if (peoples.isNotEmpty()){
                    binding.includeEmptyList.root.visibility = View.GONE
                } else {
                    binding.includeEmptyList.root.visibility = View.VISIBLE
                }
            }
        }

        lifecycleScope.launch{
            viewModel.skeletonsShowState.collect{ visible->
                if (visible) {
                    binding.recyclerView.veil()
                    binding.includeEmptyList.root.visibility = View.GONE
                } else {
                    binding.recyclerView.unVeil()
                    binding.swipeRefreshLayout.isRefreshing = false
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
        lifecycleScope.launch{
            viewModel.sortByState.collect{sort->
                adapter.dobSort(sort)

            }
        }



        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.setRefreshData()
            }
            swipeRefreshLayout.setColorSchemeColors(
                requireContext().getColor(R.color.purple)
            )

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

        binding.buttonFilter.setOnClickListener {
            showDialog()
        }

    }

    private fun showDialog() {
        val dialog = Dialog(requireContext())

        val binding = PartBottomSheetDialogBinding.inflate(layoutInflater)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            dialog.window?.insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            @Suppress("DEPRECATION")
            dialog.window?.decorView?.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    )
        }

        binding.apply {
            radioGroup.check(
                when(viewModel.sortByState.value){
                    SortBy.Alphabet -> radioButtonAlphabet.id
                    SortBy.Dob -> radioButtonDob.id
                }
            )

            radioGroup.setOnCheckedChangeListener { _, id ->
                when(id){
                    radioButtonAlphabet.id -> {
                        viewModel.setSort(SortBy.Alphabet)
                    }
                    radioButtonDob.id -> {
                        viewModel.setSort(SortBy.Dob)
                    }
                }
            }

        }

    }

    override fun onClickPeople(people: People) {
        val bundle = Bundle()
        bundle.putSerializable(Constance.DATA_TRANSFER_KEY, people)
        controller.navigate(R.id.detailsFragment, bundle)
    }
}