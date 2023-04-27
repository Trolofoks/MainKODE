package com.honey.mainkode.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
abstract class BaseFragment<VB : ViewBinding, VM: ViewModel>(
    private val bindingInflater: (inflater : LayoutInflater) -> VB,
    private val viewModelClass : KClass<VM>
) : Fragment() {

    private var _viewModel : ViewModel? = null

    val viewModel: ViewModel
        get() = _viewModel as ViewModel

    private var _binding: VB? = null

    val binging : VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewModel = ViewModelProvider(this).get(viewModelClass.java)

        _binding = bindingInflater.invoke(inflater)
        if (_binding== null)
            throw IllegalArgumentException("Binding cannot be null")
        return binging.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}