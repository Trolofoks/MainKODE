package com.honey.mainkode.ui.fragments.error

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.honey.mainkode.R
import com.honey.mainkode.base.BaseFragment
import com.honey.mainkode.databinding.FragmentErrorBinding
import com.honey.mainkode.model.Constance
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ErrorFragment : BaseFragment<FragmentErrorBinding, ErrorViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textButtonRetry.setOnClickListener {
            viewModel.retryButtonClicked()
        }

        lifecycleScope.launch{
            viewModel.navigateToMainShar.collect{nav->
                if (nav) controller.navigateUp()
            }
        }
    }
}