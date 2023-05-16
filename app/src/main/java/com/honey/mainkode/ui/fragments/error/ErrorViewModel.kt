package com.honey.mainkode.ui.fragments.error

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ErrorViewModel @Inject constructor() : ViewModel(){

    private val _navigateToMainShar = MutableSharedFlow<Boolean>()
    val navigateToMainShar : SharedFlow<Boolean> = _navigateToMainShar.asSharedFlow()

    fun retryButtonClicked(){
        viewModelScope.launch {
            _navigateToMainShar.emit(true)
        }
    }
}