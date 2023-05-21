package com.honey.mainkode.ui.fragments.main

import android.os.Bundle
import android.view.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.honey.mainkode.R
import com.honey.mainkode.adapter.Listener
import com.honey.mainkode.adapter.PeoplesAdapter
import com.honey.mainkode.base.BaseFragment
import com.honey.mainkode.databinding.FragmentMainBinding
import com.honey.mainkode.model.Constance
import com.honey.mainkode.ui.fragments.main.helper.DialogHelper
import com.honey.mainkode.ui.fragments.main.helper.UserInputHelper
import com.honey.mainkode.ui.fragments.main.helper.ViewModelObserverHelper
import com.honey.model.People
import dagger.hilt.android.AndroidEntryPoint

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

        val dialogHelper = DialogHelper(binding, viewModel, requireContext())

        val userInputSetupHelper = UserInputHelper(binding, requireActivity(), requireContext(), dialogHelper)
        userInputSetupHelper.setupObservers(viewModel)

        val viewModelObserverHelper = ViewModelObserverHelper(viewModel, adapter, viewLifecycleOwner.lifecycleScope)
        viewModelObserverHelper.setupObservers(binding, controller)
    }

    override fun onClickPeople(people: People) {
        val bundle = Bundle()
        bundle.putSerializable(Constance.DATA_TRANSFER_KEY, people)
        controller.navigate(R.id.detailsFragment, bundle)
    }


}