package com.honey.mainkode.ui.fragments.main.helper

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayout
import com.honey.mainkode.R
import com.honey.mainkode.databinding.FragmentMainBinding
import com.honey.mainkode.ui.fragments.main.MainViewModel

class UserInputHelper(
    private val binding: FragmentMainBinding,
    private val activity: FragmentActivity,
    private val context: Context,
    private val dialogHelper: DialogHelper,
) {
    fun setupObservers(viewModel: MainViewModel) {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.setRefreshData()
            }
            swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(context, R.color.purple)
            )

            editTextSearch.setOnFocusChangeListener { _, focused ->
                if (focused) {
                    textButtonCancel.visibility = View.VISIBLE
                    buttonSearch.setColorFilter(ContextCompat.getColor(context, R.color.black))
                } else {
                    textButtonCancel.visibility = View.GONE
                    buttonSearch.clearColorFilter()
                }
            }

            textButtonCancel.setOnClickListener {
                editTextSearch.clearFocus()
                editTextSearch.setText("")
                val inputMethodManager =
                    getSystemService(activity, InputMethodManager::class.java)
                inputMethodManager?.hideSoftInputFromWindow(editTextSearch.windowToken, 0)
            }

            editTextSearch.addTextChangedListener { value ->
                viewModel.setSearchField(value.toString())
            }

            include.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewModel.setSelectTab(tab)
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

            binding.buttonFilter.setOnClickListener {
                dialogHelper.showDialog()
            }
        }
    }
}
